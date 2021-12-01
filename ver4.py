
import re
import string 
sen = []
tweetIds = []
with open("Trec_microblog11.txt") as f:
    for line in f:
        tweetID = line[:18]
        tweet = line[18:]
        cleanLine = re.sub(r'\w+:\/{2}[\d\w-]+(\.[\d\w-]+)*(?:(?:\/[^\s/]*))*', '', tweet)
        table = str.maketrans(dict.fromkeys(string.punctuation)) 
        new_s = cleanLine.translate(table)
        sen.append(new_s)
        tweetIds.append(tweetID)

print(sen)
print(tweetIds)

from sentence_transformers import SentenceTransformer
model = SentenceTransformer('bert-base-nli-mean-tokens')
#Encoding:
sen_embeddings = model.encode(sen)

## queries 
with open("topics_MB1-49.txt") as f:
    for row in f:
        if (row.startswith("<title>")):
            start = row.find("<title>") + len("<title>")
            end = row.find("</title>")
            query = row[start:end]    
            print(query)


            query_embed = model.encode(query)

            from sklearn.metrics.pairwise import cosine_similarity
            #let's calculate cosine similarity for sentence 0:

            print("calculating similarity")
            # print(cosine_similarity([query_embed],sen_embeddings[0:]))

            for i in cosine_similarity([query_embed],sen_embeddings[0:]):
                result = list(enumerate(i))
                print(result)
                list3 = sorted(result, reverse= True, key = lambda x: x[1])
                print(list3)
                
                for x in list3:
                    print (tweetIds[x[0]])
