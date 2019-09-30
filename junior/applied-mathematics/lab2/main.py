from lib import HuffmanWrapper
from lib import ShannonFanoWrapper
from tabulate import tabulate
import os

def main():
    text_length = 0
    probs = {}

    file_path = input("Путь к файлу: ")
    if not os.path.exists(file_path):
        print("Такого файла не существует!")
    else:
        with open(file_path, 'r') as file:
            for char in file.read():
                if char.isalpha() or char.isdigit():
                    char_temp = char.upper()
                elif char == ' ':
                    char_temp = " " 
                else:
                    char_temp = '.'

                if char_temp in probs:
                    probs[char_temp] += 1.0
                else:
                    probs[char_temp] = 1.0

                text_length += 1

        for prob_key in probs:
            probs[prob_key] = probs[prob_key]/text_length

        huffman_res = HuffmanWrapper.huffman(probs)
        data_list = []
        for prob_key in probs:
            data_list.append([prob_key, probs[prob_key], huffman_res[prob_key]])
        table = tabulate(data_list, headers=['Символ', 'Вероятность', 'Код'],
                        tablefmt='orgtbl')
        print(table)

if __name__ == "__main__":
    main()
