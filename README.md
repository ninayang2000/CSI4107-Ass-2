# CSI4107

**Team members**

Anna Yang - 300272200

Nina Yang - 300271696

Eve Alshehri - 300023661


# Intro 

Out of the three experiments - we decided to perform a modified version of experiment 2 and 3
1. Use your system for Assignment 1 to produce initial results (1000 documents for each query), then re-rank them based on a new similarity scores between the query and each selected document. You can produce vectors for the query and each of the selected documents using various versions of sent2vec, doc2vec, BERT, or the universal sentence encoder. You can also use pre-trained word embeddings and assemble them to produce query/document embeddings.

2. Query vector modification or query expansion based on pretrained word embeddings or other methods. For example, add synonyms to the query if there is similarity with more than one word in the query (or with the whole query vector). You can use pre-trained word embeddings (such as FastText, word2vec, GloVe, and others), preferably some build on a Twitter corpus, to be closer to your collection of documents.

3. Use BERT or other neural models from the beginning, without the need on an initial classical IR system, to compute the similarity between the query and every document in the collection. This probably will take too long time, but look for ways to reduce the number of operations needed, especially if your system for Assignment 1 is not functional. For example, you can use a simple boolean index to restrict the calculations only to documents that have at least one query word.

for experiment 2 we utilised word2vec to produce vectors for each words whereas in experiment 3 we utilised bert to produce vectors for each document. After that we utilised cosine similarity to compare and rank the documents for each query. 

# **Work distribution**
We took a divide and conquer approach to complete this assignment. To manage all our code we utilised Github. We divided the assignment into four main steps and assigned members to be in charge of completing particular parts. 

1. Converting results of assignment 1 into python so that we could run experiments - Nina
2. Experiment 3 - Anna
3. Experiment 2 - Eve
4. Report - Nina

Assignment 1 was done in Java, however, BERT was most easily executed in Python.  As such, there was quite a lot of reformatting that had to be done to the results of our original assignment for us to proceed. Nina was in charge of this. Following on, Anna and Eve conducted an experiment each. Finally, Nina put together the final report and summarised all findings. 



# **Functionality of program**

The program implements an improved Information Retrieval System based for a collection of Twitter messages. In the previous assignment, we created a ‘Tweet’ class which stored the Tweet Id and message. We used a hash map for the inverted index where it uses the tokens obtained from the preprocessing module as the key and an array of tuples which contain a tweetID and word frequency as the associated value.

In this assignment we represented words and sentences in a vector form and utilised more recent neural information retrieval methods such as BERT to achieve better evaluation scores. In the experiment3, each sentence was represented as a vector and then we used cosine to find similarity.

Experiment 3 is located in experiment3.py and experiment 2 is located in experiment2.py. Our retrieval system requires Trec_microblog11.txt, Trec_microblog11-qrels.txt and StopWords.txt as input files. The results of each query can be found in results3.txt and results2.txt respectively. 


# **Instructions on how to run program**

Please follow the following steps to run our program:

**Experiment 3**

1. Clone repository submitted
2. Install the sentence_transformer library through the command: pip install sentence-transformers
3. Install the scikit-learn library through the command line: pip install -U scikit-learn
4. Run the experiment3.py through the command line: python3 experiment3.py
5. The results should now be in a file named “Results3.txt”. There is also a “Expected_results3.txt” file, which is what the Results3.txt is meant to look like. Please note that due to the large amount of tweets this program takes around 1 hour to fully process. 


**Experiment 2**

1. Clone repository submitted
2. Install the gensim library through the command: pip install gensim
3. Install the numpy library through the command: pip install numpy
4. Download the model through this link: https://drive.google.com/file/d/0B7XkCwpI5KDYNlNUTTlSS21pQmM/edit?resourcekey=0-wjGZdNAUop6WykTtMip30g and save the file into the folder where all the other documents are 
5. Run the experiment2.py file through the command line: python3 experiment2.py
6. Please note that the docsim.py file was copied from https://github.com/v1shwa/document-similarity which falls under the permissions of the [MIT License](https://github.com/v1shwa/document-similarity/blob/master/LICENSE) which allows us to obtain a copy of this software without restriction
7. The results should now be in a file named “Results2.txt”. There is also a “Expected_results2.txt” file, which is what the Results2.txt is meant to look like. Please note that due to the large amount of tweets this program takes around 1 hour to fully process. 



# **Steps**

**1. Preprocessing**


Preprocessing was implemented through a number of sequential steps. In preparation for this, a stopword list was created. This was done by looking through stopwords.txt and inserting all the stop words into the list. Note, all the stop words are in lower case. A stemmer was not used. 

After opening up the microblog file, each line was read one by one. Within each line, the url was taken out, and the punctuation was replaced by spaces. Following, the line was split by spaces into words, which were then changed to lowercase. This step was necessary so that the appropriate stop words could be easily compared and extracted in the latter step. 

Finally, the cleaned sentences which had the stopwords removed were joined back together into a sentence and inserted into a sentence array. 


**2. Vectorising**


From "topics_MB1-49.txt", we extracted each query and converted it into a vector. We then compared the query vector to each sentence vector to compute a cosine similarity score. 

We then ranked all the documents from largest to smallest similarity score and identified the top-performing method. The results of this were placed in results.txt.  

# **Algorithms, data structures and optimisations**

**BERT**
Bidirectional Encoder Representation from Transformers (BERT) is a technique for natural language processing pre-training developed by Google. BERT is trained on unlabelled text including Wikipedia and Book corpus. We used a pre-trained BERT model from Huggingface to embed our corpus. We loaded the BERT base model, which has 12 layers (transformer blocks), 12 attention heads, 110 million parameters and a hidden size of 768. We then used the  best-base-no-mean-tokens model to convert the sentences into vectors. 

**word2vec**
As the name suggests word2vec embeds words into vector space. Word2vec takes a text corpus as input and produce word embeddings as output. You are able to train our own embeddings if have enough data and computation available or we can use pre-trained embeddings. We will use a pre-trained embedding provided by Google.

**Similarity**
We used Cosine Distance to calculate the similarity between two documents. Cosine distance is the cosine of the angle between two vectors, which gives us the angular distance between the vectors. Formula to calculate cosine similarity between two vectors A and B is,
<img width="387" alt="Picture 2" src="https://user-images.githubusercontent.com/68538906/144458328-054fdf42-c941-4b3a-9c3f-2bd7274fdcf5.png">



To do this we used the cosine_similiarity function from the sklearn library. 


**First 10 answers to query 3 from best system**
```
3 Q0 29278582916251649	1 0.9030514 myRun
3 Q0 34410414846517248	2 0.8982343 myRun
3 Q0 35088534306033665	3 0.89789546 myRun
3 Q0 33254598118473728	4 0.8800545 myRun
3 Q0 34682906718908416	5 0.8780651 myRun
3 Q0 29296574815272960	6 0.8724812 myRun
3 Q0 32809006015713280	7 0.8602185 myRun
3 Q0 32273316047757312	8 0.85713917 myRun
3 Q0 34728356083666945	9 0.8556447 myRun
3 Q0 32204788955357184	10 0.8442358 myRun
```

**First 10 answers to query 20 from best system**
```
20 Q0 30245154598158337 1 0.9095361 myRun
20 Q0 31747239311314944 2 0.9091542 myRun
20 Q0 30986955508424704 3 0.87753177 myRun
20 Q0 31777427092938752 4 0.86503893 myRun
20 Q0 31492472911691776 5 0.8605448 myRun
20 Q0 29958466130939904 6 0.8590292 myRun
20 Q0 31948737517453312 7 0.8533156 myRun
20 Q0 33247153149190144 8 0.852332 myRun
20 Q0 30727342653444098 9 0.84897375 myRun
20 Q0 31951627434860544 10 0.8485288 myRun

```

# Evaluation

## Results

**Experiment 3**

After we placed the results.txt file in the same folder as the TREC_EVAL code we ran the following command:
./trec_eval Trec_microblog11-qrels.txt results.txt

This gave us the following results:

<img width="323" alt="Picture 1" src="https://user-images.githubusercontent.com/68538906/144455476-d010811b-1845-474a-a8fc-28421e397bc3.png">

below is the results for experiment 2:

![image](https://user-images.githubusercontent.com/68418366/144532333-08c26369-edb5-48c5-9b56-915a53bd5daf.png)



When comparing this with results from experiment one (below), it is evident that both experiments did not perform as well
```
num_q                   all     49
num_ret                 all     36347
num_rel                 all     2640
num_rel_ret             all     2055
map                     all     0.2658
gm_map                  all     0.1226
Rprec                   all     0.2817
bpref                   all     0.2955
recip_rank              all     0.5354
iprec_at_recall_0.00    all     0.6149
iprec_at_recall_0.10    all     0.4726
iprec_at_recall_0.20    all     0.4199
iprec_at_recall_0.30    all     0.3566
iprec_at_recall_0.40    all     0.3327
iprec_at_recall_0.50    all     0.2924
iprec_at_recall_0.60    all     0.2254
iprec_at_recall_0.70    all     0.1859
iprec_at_recall_0.80    all     0.1550
iprec_at_recall_0.90    all     0.1059
iprec_at_recall_1.00    all     0.0371
P_5                     all     0.3837
P_10                    all     0.3163
P_15                    all     0.3048
P_20                    all     0.2898
P_30                    all     0.2653

P_100                   all     0.1814
P_200                   all     0.1268
P_500                   all     0.0732
P_1000                  all     0.0419


```

## Discussion of results

### Experiment 3(BERT) vs assignment 1 

Interestingly, employing the BERT method did not lead to better results for us. Both our MAP and P@10 scores were lower than what we achieved in assignment 1. A reason for assignment 1 performing so well may be that the queries and the tweets have large lexical similarity. Moreover, BERT took a lot longer to run when compared to assignment 1, suggesting that it is less efficient. Since we have included all of the documents in the encoding for the comparison with the query, this could lead to diminishing the context of dependency of BERT which we suspect 

### Experiment 2(word2vec) vs assignment 1

It is known Word2Vec isn't able to capture word relationships in the latant embedding space with limited information. It is possible that the lower performance could be due to the relatively small size of the corpus with respect to what is needed for the model to establish the syntatic and semantic relationships that would lead to a higher score.


### Comparison of experiment 2 (word2vec) and 3 (BERT)

Word2Vec models generate embeddings that are context-independent: ie - there is just one vector (numeric) representation for each word. Different senses of the word (if any) are combined into one single vector.
However, the BERT model generates embeddings that allow us to have multiple (more than one) vector (numeric) representations for the same word, based on the context in which the word is used. Thus, BERT embeddings are context-dependent and therefore produced better results than word2vec. 
 
