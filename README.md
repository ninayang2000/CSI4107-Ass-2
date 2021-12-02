# CSI4107

**Team members**

Anna Yang - 300272200

Nina Yang - 300271696

Eve Alshehri - 300023661


# Intro 

Out of the three experiments - we decided to perform experiment 1 and 3
1. Use your system for Assignment 1 to produce initial results (1000 documents for each query), then re-rank them based on a new similarity scores between the query and each selected document. You can produce vectors for the query and each of the selected documents using various versions of sent2vec, doc2vec, BERT, or the universal sentence encoder. You can also use pre-trained word embeddings and assemble them to produce query/document embeddings.

2. Query vector modification or query expansion based on pretrained word embeddings or other methods. For example, add synonyms to the query if there is similarity with more than one word in the query (or with the whole query vector). You can use pre-trained word embeddings (such as FastText, word2vec, GloVe, and others), preferably some build on a Twitter corpus, to be closer to your collection of documents.

3. Use BERT or other neural models from the beginning, without the need on an initial classical IR system, to compute the similarity between the query and every document in the collection. This probably will take too long time, but look for ways to reduce the number of operations needed, especially if your system for Assignment 1 is not functional. For example, you can use a simple boolean index to restrict the calculations only to documents that have at least one query word.

# **Work distribution**
We took a divide and conquer approach to complete this assignment. To manage all our code we utilised Github. We divided the assignment into four main steps and assigned members to be in charge of completing particular parts. 

1. Converting results of assignment 1 into python so that we could run experiments - Nina
2. Experiment 3 - Anna
3. Experiment 2 - Eve
4. Report - Nina

Assignment 1 was done in Java, however, BERT was most easily executed in Python.  As such, there was quite a lot of reformatting that had to be done to the results of our original assignment for us to proceed. Nina was in charge of this. Following on, Anna and Eve conducted an experiment each. Finally, Nina put together the final report and summarised all findings. 



# **Functionality of program**

The program implements an improved Information Retrieval System based for a collection of Twitter messages. In the previous assignment, we created a ‘Tweet’ class which stored the Tweet Id and message. We used a hash map for the inverted index where it uses the tokens obtained from the preprocessing module as the key and an array of tuples which contain a tweetID and word frequency as the associated value.

In this assignment we represented words and sentences in a vector form and utilised more recent neural information retrieval methods such as BERT to achieve better evaluation scores. In the first experiment, each sentence was represented as a vector, whereas in the second experiment, each word was represented as a vector. 

Experiment 3 is located in experiment3.py and experiment 2 is located in experiment2.py. Our retrieval system requires Trec_microblog11.txt, Trec_microblog11-qrels.txt and StopWords.txt as input files. The results of each query can be found in results3.txt and results2.txt respectively. 


# **Instructions on how to run program**

Please follow the following steps to run our program:

**Experiment 3**

1. Install the sentence_transformer library through the command: pip install sentence-transformers
2. Install the scikit-learn library through the command line: pip install -U scikit-learn
3. Run the experiment1.py through the command line: python3 experiment1.py
4. The results should now be in a file named “results.txt”. There is also a “expected_results.txt” file, which is what the results.txt is meant to look like. Please note that due to the large amount of tweets this program takes around 1 hour to fully process. 

# **Steps**

**1. Preprocessing**


Preprocessing was implemented through a number of sequential steps. In preparation for this, a stopword list was created. This was done by looking through stopwords.txt and inserting all the stop words into the list. Note, all the stop words are in lower case. A stemmer was not used. 

After opening up the microblog file, each line was read one by one. Within each line, the url was taken out, and the punctuation was replaced by spaces. Following, the line was split by spaces into words, which were then changed to lowercase. This step was necessary so that the appropriate stop words could be easily compared and extracted in the latter step. 

Finally, the cleaned sentences which had the stopwords removed were joined back together into a sentence and inserted into a sentence array. 


**2. Indexing**


From "topics_MB1-49.txt", we extracted each query and converted it into a vector. We then compared the query vector to each sentence vector to compute a cosine similarity score. 

We then ranked all the documents from largest to smallest similarity score and identified the top-performing method. The results of this were placed in results.txt.  

# **Algorithms, data structures and optimisations**

**BERT**
Bidirectional Encoder Representation from Transformers (BERT) is a technique for natural language processing pre-training developed by Google. BERT is trained on unlabelled text including Wikipedia and Book corpus. We used a pre-trained BERT model from Huggingface to embed our corpus. We loaded the BERT base model, which has 12 layers (transformer blocks), 12 attention heads, 110 million parameters and a hidden size of 768. We then used the  best-base-no-mean-tokens model to convert the sentences into vectors. 

**Similarity**
We used Cosine Distance to calculate the similarity between two documents. Cosine distance is the cosine of the angle between two vectors, which gives us the angular distance between the vectors. Formula to calculate cosine similarity between two vectors A and B is,
<img width="387" alt="Picture 2" src="https://user-images.githubusercontent.com/68538906/144458328-054fdf42-c941-4b3a-9c3f-2bd7274fdcf5.png">



To do this we used the cosine_similiarity function from the sklearn library. 


**First 10 answers to query 1**
```
1 Q0 30260724248870912 1 1.0 myRun
1 Q0 30198105513140224 2 0.9936888200553103 myRun
1 Q0 30016851715031040 3 0.8857852417000207 myRun
1 Q0 30016488928706560 4 0.8857852417000207 myRun
1 Q0 30167063326629888 5 0.8857852417000207 myRun
1 Q0 30275282464153600 6 0.8857852417000207 myRun
1 Q0 30244402504929280 7 0.8463497262447456 myRun
1 Q0 30236884051435520 8 0.8463497262447456 myRun
1 Q0 30554037510213632 9 0.8463497262447456 myRun
1 Q0 30500781002063872 10 0.8463497262447456 myRun
```

**First 10 answers to query 25**
```
25 Q0 31738694356434944 1 1.0 myRun
25 Q0 32609015158542336 2 1.0 myRun
25 Q0 31550836899323904 3 1.0 myRun
25 Q0 31286354960715777 4 1.0 myRun
25 Q0 30093525102108674 5 0.9008951031334103 myRun
25 Q0 32685391781830656 6 0.9008951031334103 myRun
25 Q0 32528974961713152 7 0.9008951031334103 myRun
25 Q0 31320463862931456 8 0.9008951031334103 myRun
25 Q0 32541161675558912 9 0.893156366056288 myRun
25 Q0 29974357501550592 10 0.7988958504439189 myRun
```

# Evaluation

# BERT

After we placed the results.txt file in the same folder as the TREC_EVAL code we ran the following command:
./trec_eval Trec_microblog11-qrels.txt results.txt

This gave us the following results:

<img width="323" alt="Picture 1" src="https://user-images.githubusercontent.com/68538906/144455476-d010811b-1845-474a-a8fc-28421e397bc3.png">

When comparing this with results from experiment one (below), it is evident that BERT performance is not as good. 
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

# Discussion of results

**Experiment 3** 
Interestingly, employing the BERT method did not lead to better results for us. Both our MAP and P@10 scores were lower than what we achieved in assignment 1. A reason for assignment 1 performing so well may be that the queries and the tweets have large lexical similarity. Moreover, BERT took a lot longer to run when compared to assignment 1, suggesting that it is less efficient. 

 
