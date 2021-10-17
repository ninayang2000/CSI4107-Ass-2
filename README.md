# CSI4107

**Team members**


Anna Yang - 300272200

Nina Yang - 300271696

Eve Alshehri - 300023661




# **Work distribution**
We took a divide and conquer approach to complete this assignment. To manage all our code we utilised Github. 
1. Nina was in charge of step 1 - preprocessing. 
2. Anna was in charge of step 2 - Indexing 
3. Eve was in charge of step 3 - Retrieval and Ranking. 

Since each subsequent step required input from the previous step, each of us tried to work fast and we explained our code and answered each other's questions. 
After the basic retrieval system was completed, all members brainstormed ways we could optimize and improve the system. Finally, Anna and Nina worked on the evaluation and results file as well as summarising our findings in the report. 


# **Functionality of program**

The program implements an Information Retrieval System based for a collection of Twitter messages. The system is located in RetrievalSystem.java and calls on functions from other java files based on functionality. We used java to write the program. 
Our retrieval system requires Trec_microblog11.txt, Trec_microblog11-qrels.txt and StopWords.txt as input files. The results of each query can be found in results.txt.


# **Instructions on how to run program**

Please follow the following steps to run our program:

1. Open up the folder called submission 
2. Open up the file named “RetrievalSystem.java”
3. Run the main function
4. The results should now be in a file named “results.txt” (please note that after you have run the program once, you will have to clear the results.txt file if you wish to run it again. If not cleared, there will be double the results. There is also a “PreRunResults.txt” file, which is what the results.txt is meant to look like) 


# Algorithms, data structures and optimisations

**1. Preprocessing**

Preprocessing was implemented through a number of sequential steps. In preparation for this, a stopword file was created. This was done by inserting all the stop words into an array. Note, all the stop words are in lower case. A stemmer was not used. 

After opening up the microblog file, each line was read one by one. Within each line, the url was taken out, and the punctuation was replaced by spaces. Following, the line was split by spaces into words, which were then changed to lowercase. This step was necessary so that the appropriate stop words could be easily compared and extracted in the latter step. Following on, these altered words were added to an array, and the stop words were removed. 

Finally, the preprocessed words were added to another file named preprocessingresult.txt’, and this completes the entire preprocessing step. 

In the preprocessing step, we used two main data structures which are array lists and sets respectively. The set allowed us to avoid duplicate words from being inserted, whilst the array list simplified the stop words removal process since we could utilise built in functions. 


**2. Indexing**

Before constructing the index, we first read through each of the tweets in Trec_microblog11.txt and stored the tweetID and message in a “tweet” class that we made.

We used a hash map for the inverted index where it uses the tokens obtained from the preprocessing module as the key and an array of tuples which contain a tweetID and word frequency as the associated value. An example is shown below:
```
world [DF: 2]-> (952194402811904, 1)(349521863287848, 2)
question [DF:1]-> {349520414155816,3)
```

Initially our code was extremely inefficient and it took a tremendous amount of time to construct the inverted index. This was because we were looping through the preprocessed words and for each word we were looking through every tweet to see if that word appeared in the tweet ie.
```
For preprocessed word in preprocessed word list {
	For tweet in collection {
		For word in tweet {
			… 
		}
	}
}
```


After some analysis and careful consideration we decided to switch the order of the for loops by looking through the tweets and for each word in the tweet looking through the preprocessed words ie. 
```
For tweet in collection {
	For word in tweet {
		For preprocessed word in preprocessed word list {
			… 
		}
	}
}
```

This simple change sped up the construction of the inverted exponentially. 


**3. Retrieving and ranking**



Create “Query” class
A query class was created which consisted of the query number and the query itself. 

Extract query and query number 
From "topics_MB1-49.txt", we extracted each query and its query number by creating a new instance of a query class. These extracted queries were then added to an array.

Loop through array and evaluate each query
We performed retrieval and ranking for each query based on the following steps after splitting each query to individual words. We made sure to transform each of these words to lowercase so that it matched exactly with our preprocessed words list. 



**4. Run program to see results.txt**

Please note that after you have run the program once, you will have to clear the results.txt file if you wish to run it again. If not cleared, there will be double the results. There is also a “PreRunResults.txt” file, which is what the results.txt is meant to look like. 


# Sample Results

The vocabulary size is 59,413 words 

Sample of 100 tokens from vocabulary 
```

priscilla
manjat
manjar
tripping
chilly
tnfishermen
salespeople
dummy
frustrations
discharged
chills
flashing
rimborsato
floo
flop
flor
flow
chilli
brandweer
misspell
weltweit
resonance
floe
msfixx
flog
lovefb
remis
remit
fuckwits
ehabz
segel
lilastranomical
makassar
remix
vaccination
remia
ldoren
pellet
ladyofarr
dumps
remin
happyhunting
sevirifell
ramsincanon
bothering
visitando
acopatunru
dumpa
technica
humanitynews
redundancy
ipatter
avail
dumol
hastings
eugen
barclaycard
ugsoles
srvs
kristen
indonesiana
figaropravda
ragelewis
ofis
mogli
kentuckiana
avait
eurasia
virusfreephone
avaaz
automation
djjarrao
yuhuuuuuuuuuuuuuuu
leicester
mogov
sneaky
pigeon
unconference
circumstances
adriana
timed
nazar
baltic
sneaks
conveniently
chilis
mystifying
timee
lieutenantloker
ricardogardea
juakhumalo
segal
footer
spliced
odessa
observered
kenyang
timer
honecker
lanarak
```

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
After we placed the results.txt file in the same folder as the TREC_EVAL code we ran the following command:
./trec_eval Trec_microblog11-qrels.txt results.txt


This gave us the following results:
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
We were very happy with our results which ended up being: MAP at around 0.2658, P@5 at 0.3837 and P@10 at 0.3163. If we had more time we would have liked to implement stemming and perhaps looked into separating concatenated tokens such as those of hashtags. We believe that the previously mentioned methods could have helped improve our results. However, in saying such, we believe that our current system provides solid and adequate retrieval and ranking of results. 
