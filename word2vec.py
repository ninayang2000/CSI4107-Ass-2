from gensim.models.keyedvectors import KeyedVectors
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from scipy import spatial
import re
import string 

model_path = 'GoogleNews-vectors-negative300.bin'
w2v_model = KeyedVectors.load_word2vec_format(model_path, binary=True)

# Docsim is a library 
from docsim import DocSim
ds = DocSim(w2v_model)

stopwords = []
with open("stopWords.txt") as sw:

    for line in sw:
        stopwords.append(line.strip())


sen = []
tweetIds = []
# with open("Trec_microblog11.txt") as f:
with open("test.txt") as f:

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




with open("query.txt") as f:
    # with open('updated_results.txt', 'w') as res:
    #     queryID = 1 
    for row in f:
        if (row.startswith("<title>")):
            start = row.find("<title>") + len("<title>")
            end = row.find("</title>")
            query = row[start:end]    
            print(query)
            sim_scores = ds.calculate_similarity(query, sen)
            print(sim_scores)


# source_doc = 'how to delete an invoice'
# target_docs = ['delete a invoice', 'how do i remove an invoice', "purge an invoice"]

# sim_scores = ds.calculate_similarity(source_doc, target_docs)

# print(sim_scores)
# for s in sen:
#     sim_scores = ds.calculate_similarity(s, sen)