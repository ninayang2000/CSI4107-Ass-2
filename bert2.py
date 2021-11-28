from transformers import BertTokenizer, BertModel 
from scipy.spatial.distance import cosine


# Load the BERT tokenizer.
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased', do_lower_case=True)

sentences = "Save BBC World Service from Savage Cuts"


# Print the original sentence.
print(' Original: ', sentences)

# Print the sentence split into tokens.
print('Tokenized: ', tokenizer.tokenize(sentences))

# Print the sentence mapped to token ids.
print('Token IDs: ', tokenizer.convert_tokens_to_ids(tokenizer.tokenize(sentences)))

input_ids = tokenizer.encode(sentences, add_special_tokens=True)

print('final: ', input_ids)

model = BertModel.from_pretrained('bert-base-uncased', output_hidden_states = True,)

# Put the model in "evaluation" mode, meaning feed-forward operation.
model.eval()