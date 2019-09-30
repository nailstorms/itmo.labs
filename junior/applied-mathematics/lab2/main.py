from lib import HuffmanWrapper
from lib import ShannonFanoWrapper
from lib import ShannonFanoNode
from tabulate import tabulate
import os
import collections

def main():
    text_length = 0
    probs = {}
    nodes = []

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
        
        sorted_probs = collections.OrderedDict(sorted(probs.items(), key=lambda x: x[1], reverse=True))

        for prob_key in sorted_probs:
            sorted_probs[prob_key] = sorted_probs[prob_key]/text_length
            nodes.append(ShannonFanoNode(prob_key, sorted_probs[prob_key], ""))


        ''' Хаффман '''

        huffman_res = HuffmanWrapper.huffmanize(probs)
        data_list = []
        for prob_key in sorted_probs:
            data_list.append([prob_key, sorted_probs[prob_key], huffman_res[prob_key], len(huffman_res[prob_key])])
        table = tabulate(data_list, headers=['Символ', 'Вероятность', 'Код', 'Длина кода'],
                        tablefmt='orgtbl')
        print(f'\nТаблица для кодов Хаффмана:\n\n{table}')

        avg_huffman_length = 0.0
        for prob_key in huffman_res:
            avg_huffman_length += len(huffman_res[prob_key])
        avg_huffman_length /= len(huffman_res)
        print(f'\nСредняя длина кодов Хаффмана: {avg_huffman_length}')


        ''' Шэннон-Фано '''

        ShannonFanoWrapper.shannon_fanonize(nodes)
        data_list = []
        for item in nodes:
            data_list.append([item.char, item.prob, item.code, len(item.code)])
        table = tabulate(data_list, headers=['Символ', 'Вероятность', 'Код', 'Длина кода'],
                        tablefmt='orgtbl')
        print(f'\n\nТаблица для кодов Шэннона-Фано:\n\n{table}')
        
        avg_shannon_fano_length = 0.0
        for item in nodes:
            avg_shannon_fano_length += len(item.code)
        avg_shannon_fano_length /= len(nodes)
        print(f'\nСредняя длина кодов Шэннона-Фано: {avg_shannon_fano_length}')


if __name__ == "__main__":
    main()
