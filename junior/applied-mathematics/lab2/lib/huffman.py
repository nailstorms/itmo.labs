class HuffmanWrapper:

    @staticmethod
    def huffmanize(prob_dict):

        if(len(prob_dict) == 2):
            return dict(zip(prob_dict.keys(), ['0', '1']))

        prob_dict_temp = prob_dict.copy()
        letter_1, letter_2 = HuffmanWrapper.lowest_prob_pair(prob_dict)
        prob_1, prob_2 = prob_dict_temp.pop(letter_1), prob_dict_temp.pop(letter_2)
        prob_dict_temp[letter_1 + letter_2] = prob_1 + prob_2

        huffman_code_dict = HuffmanWrapper.huffmanize(prob_dict_temp)
        huffman_node = huffman_code_dict.pop(letter_1 + letter_2)
        huffman_code_dict[letter_1], huffman_code_dict[letter_2] = huffman_node + '0', huffman_node + '1'

        return huffman_code_dict

    @staticmethod
    def lowest_prob_pair(prob_dict):
        sorted_prob_dict = sorted(prob_dict.items(), key=lambda x: x[1])
        return sorted_prob_dict[0][0], sorted_prob_dict[1][0]
