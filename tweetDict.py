
# tweetList = {}

# with open("Trec_microblog11.txt") as f:
#     for line in f:
#         tweetID, value = line.split("\t")
#         tweetList[tweetID] = value

# # testing
# dict_items = tweetList.items()
# first = list(dict_items)[:10]
# print(first)

from scipy import spatial
from sent2vec.vectorizer import Vectorizer
import json
# query = ["BBC World Service staff cuts",]

# v = Vectorizer()
# v.bert(query)
# query_vectors_bert = v.vectors
# print(query_vectors_bert)

 
# Opening JSON file
f = open('data.json')
 
# returns JSON object as
# a dictionary
data = json.load(f)
 
# Iterating through the json
# list
# for i in data['tweets']:
#     if (i['title']) == 'BBC World Service staff cuts':
#         print(i['tweetID'])

for i in data['title']:
    print(i)
 
# Closing file
f.close()

# for value in data.values():
#     print(value)
