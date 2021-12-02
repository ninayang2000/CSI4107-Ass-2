from scipy import spatial
from sent2vec.vectorizer import Vectorizer
import json
# query = ["BBC World Service staff cuts",]

sentences = [
    "Three years later, the coffin was still full of Jello.",
    "The fish dreamed of escaping the fishbowl and into the toilet where he saw his friend go.",
    "The person box was packed with jelly many dozens of months later.",
    "He found a leprechaun in his walnut shell."
    "Three years later, the coffin was still full of Jello.",
]

v = Vectorizer()
v.bert(sentences)
query_vectors_bert = v.vectors



for sent in query_vectors_bert[1:]:
    dist = spatial.distance.cosine(query_vectors_bert[0], sent)
    print(dist)


# # Opening JSON file
# f = open('data.json')

# # returns JSON object as
# # a dictionary
# data = json.load(f)

# # Iterating through the json
# # list
# # for i in data['tweets']:
# #     print(i)

# # Closing file
# f.close()
# sentences = [
#     "BBC World Service staff cuts",
#     "This is an awesome book to learn NLP.",
#     "DistilBERT is an amazing NLP model.",
#     "We can interchangeably use embedding, encoding, or vectorizing.",
#     "BBC World Service staff cuts",
# ]

# # vectorizer = Vectorizer()
# # vectorizer.bert(sentences)
# # vectors_bert = vectorizer.vectors



# # for sent in vectors_bert[1:]:
# #     dist = spatial.distance.cosine(vectors_bert[0], sent)
# #     print(dist)

# vectorizer = Vectorizer()
# # vectorizer.bert(data['title'])
# # vectors_bert = vectorizer.vectors


# for i in data:
#     vectorizer.bert(i['title'])
#     vectors_bert = vectorizer.vectors
#     dist = spatial.distance.cosine(vectors_bert[0], vectors_bert)
#     print(dist)

# distance of 0 means they are identical
# dist_1 = spatial.distance.cosine(query_vectors_bert[0], vectors_bert[0])
# dist_2 = spatial.distance.cosine(vectors_bert[0], vectors_bert[2])

# print('dist_1: {0}: {1}'.format(dist_2))
