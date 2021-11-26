import torch
from transformers import BertTokenizer, BertModel
from scipy.spatial.distance import cosine



# Load pre-trained model tokenizer (vocabulary)
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')


# Define a new example sentence with multiple meanings of the word "bank"
text = "Save BBC World Service from Savage Cuts"
query = "BBC World Service staff cuts"

# Add the special tokens.
marked_text = "[CLS] " + text + " [SEP]"
query_marked_text = "[CLS] " + query + " [SEP]"

# Split the sentence into tokens.
tokenized_text = tokenizer.tokenize(marked_text)
query_tokenized_text = tokenizer.tokenize(query_marked_text)

# Print out the tokens.
print (tokenized_text)


# Map the token strings to their vocabulary indeces.
indexed_tokens = tokenizer.convert_tokens_to_ids(tokenized_text)

query_indexed_tokens = tokenizer.convert_tokens_to_ids(query_tokenized_text)

# Display the words with their indeces.
for tup in zip(tokenized_text, indexed_tokens):
    print('{:<12} {:>6,}'.format(tup[0], tup[1]))

# Mark each of the 22 tokens as belonging to sentence "1".
segments_ids = [1] * len(tokenized_text)

print (segments_ids)

for tup in zip(query_tokenized_text, query_indexed_tokens):
    print('{:<12} {:>6,}'.format(tup[0], tup[1]))

# Mark each of the 22 tokens as belonging to sentence "1".
query_segments_ids = [1] * len(query_tokenized_text)

print (query_segments_ids)

# Convert inputs to PyTorch tensors
tokens_tensor = torch.tensor([indexed_tokens])
segments_tensors = torch.tensor([segments_ids])

# Convert inputs to PyTorch tensors
query_tokens_tensor = torch.tensor([query_indexed_tokens])
query_segments_tensors = torch.tensor([query_segments_ids])

# Load pre-trained model (weights)
model = BertModel.from_pretrained('bert-base-uncased', output_hidden_states = True,)

# Put the model in "evaluation" mode, meaning feed-forward operation.
model.eval()

# Run the text through BERT, and collect all of the hidden states produced
# from all 12 layers. 
with torch.no_grad():

    outputs = model(tokens_tensor, segments_tensors)
    hidden_states = outputs[2]

with torch.no_grad():

    query_outputs = model(query_tokens_tensor, query_segments_tensors)
    query_hidden_states = query_outputs[2]




# `token_embeddings` is a [22 x 12 x 768] tensor.
# Swap dimensions 0 and 1.
token_embeddings = torch.stack(hidden_states, dim=0)

token_embeddings.size()
# Remove dimension 1, the "batches".
token_embeddings = torch.squeeze(token_embeddings, dim=1)

token_embeddings.size()
token_embeddings = token_embeddings.permute(1,0,2)

token_embeddings.size()


# `token_embeddings` is a [22 x 12 x 768] tensor.
# Swap dimensions 0 and 1.
query_token_embeddings = torch.stack(query_hidden_states, dim=0)

query_token_embeddings.size()
# Remove dimension 1, the "batches".
query_token_embeddings = torch.squeeze(query_token_embeddings, dim=1)

query_token_embeddings.size()
query_token_embeddings = query_token_embeddings.permute(1,0,2)

query_token_embeddings.size()


# Stores the token vectors, with shape [22 x 3,072]
token_vecs_cat = []

query_token_vecs_cat = []


# For each token in the sentence...
for token in token_embeddings:
    
    # `token` is a [12 x 768] tensor

    # Concatenate the vectors (that is, append them together) from the last 
    # four layers.
    # Each layer vector is 768 values, so `cat_vec` is length 3,072.
    cat_vec = torch.cat((token[-1], token[-2], token[-3], token[-4]), dim=0)
    
    # Use `cat_vec` to represent `token`.
    token_vecs_cat.append(cat_vec)

print ('Shape is: %d x %d' % (len(token_vecs_cat), len(token_vecs_cat[0])))


# For each token in the sentence...
for query_token in query_token_embeddings:
    
    # `token` is a [12 x 768] tensor

    # Concatenate the vectors (that is, append them together) from the last 
    # four layers.
    # Each layer vector is 768 values, so `cat_vec` is length 3,072.
    query_cat_vec = torch.cat((query_token[-1], query_token[-2], query_token[-3], query_token[-4]), dim=0)
    
    # Use `cat_vec` to represent `token`.
    query_token_vecs_cat.append(query_cat_vec)

print ('Shape is: %d x %d' % (len(query_token_vecs_cat), len(query_token_vecs_cat[0])))

token_vecs_sum = []

query_token_vecs_sum = []

# `token_embeddings` is a [22 x 12 x 768] tensor.

# For each token in the sentence...
for token in token_embeddings:

    # `token` is a [12 x 768] tensor

    # Sum the vectors from the last four layers.
    sum_vec = torch.sum(token[-4:], dim=0)
    
    # Use `sum_vec` to represent `token`.
    token_vecs_sum.append(sum_vec)

print ('Shape is: %d x %d' % (len(token_vecs_sum), len(token_vecs_sum[0])))


# For each token in the sentence...
for query_token in query_token_embeddings:

    # `token` is a [12 x 768] tensor

    # Sum the vectors from the last four layers.
    query_sum_vec = torch.sum(query_token[-4:], dim=0)
    
    # Use `sum_vec` to represent `token`.
    query_token_vecs_sum.append(query_sum_vec)

print ('Shape is: %d x %d' % (len(query_token_vecs_sum), len(query_token_vecs_sum[0])))

# `hidden_states` has shape [13 x 1 x 22 x 768]
# `token_vecs` is a tensor with shape [22 x 768]
token_vecs = hidden_states[-2][0]

query_token_vecs = query_hidden_states[-2][0]
# Calculate the average of all 22 token vectors.
sentence_embedding = torch.mean(token_vecs, dim=0)
print ("Our final sentence embedding vector of shape:", sentence_embedding.size())

query_sentence_embedding = torch.mean(query_token_vecs, dim=0)
print ("Our final sentence embedding vector of shape:", query_sentence_embedding.size())

# Calculate the cosine similarity between the word bank 
# in "bank robber" vs "river bank" (different meanings).
diff_bank = 1 - cosine(sentence_embedding, query_sentence_embedding)
# Calculate the cosine similarity between the word bank
# in "bank robber" vs "bank vault" (same meaning).
print('Vector similarity for *different* meanings:  %.2f' % diff_bank)