import re
import string 
from sentence_transformers import SentenceTransformer
import timeit
from sklearn.metrics.pairwise import cosine_similarity

stopwords = []
with open("stopWords.txt") as sw:
    for line in sw:
        stopwords.append(line.strip())


def common_word(str1, str2):
    if len(str1) >  len(str2):
         words1 = str2
         words2 = str1
    else:
        words1 = str1
        words2 = str2
    
    for word in words1.split():
        if word in words2:
            return True
    return False

sen = []
tweetIds = []

with  open("Trec_microblog11.txt","r", encoding="utf-8-sig") as f:
# with open("test.txt") as f:
    for line in f:
        tweetID = line[:18]
        tweet = line[18:]
        cleanLine = re.sub(r'\w+:\/{2}[\d\w-]+(\.[\d\w-]+)*(?:(?:\/[^\s/]*))*', '', tweet)
        table = str.maketrans(dict.fromkeys(string.punctuation)) 
        new_s = cleanLine.translate(table)
        words = new_s.split()
        resultwords  = [word.lower() for word in words if word.lower() not in stopwords]
        result = ' '.join(resultwords)
        sen.append(result)
        tweetIds.append(tweetID)


print(sen[0:10])
print(tweetIds[0:10])
index_sen = []
index_twtid = []
# query = "bbc"



model = SentenceTransformer('bert-base-nli-mean-tokens')
#Encoding:
# print("embedding")
start = timeit.timeit()
print("hello")
# sen_embeddings = model.encode(index_sen)

with open("topics_MB1-49.txt","r", encoding="utf-8-sig") as f:
    with open('Results3.txt', 'w') as res:
        queryID = 1 
        for row in f:
            if (row.startswith("<title>")):
                start = row.find("<title>") + len("<title>")
                end = row.find("</title>")
                query = row[start:end]    
                print(query)

                for doc, id in zip(sen,tweetIds):
                    if (common_word(query,doc)):
                        index_sen.append(doc)
                        index_twtid.append(id)

                sen_embeddings = model.encode(index_sen)    
                query_embed = model.encode(query)

                for i in cosine_similarity([query_embed],sen_embeddings[0:]):
                    result = list(enumerate(i))
                    print(result)
                    list3 = sorted(result, reverse= True, key = lambda x: x[1])
                    print(list3)
                    rank = 1
                    for x in list3:
                        res.writelines(str(queryID) + " Q0 "+tweetIds[x[0]] + str(rank) +" "+ str(x[1])+" myRun" + "\n")
                        # print (queryID, i, tweetIds[x[0]], x[1])

                        # print(x[1])
                        rank = rank +1
                res.write("\n")
                queryID = queryID + 1 

end = timeit.timeit()
print(end - start)








