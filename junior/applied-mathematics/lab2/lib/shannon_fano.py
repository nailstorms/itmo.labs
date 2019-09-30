class ShannonFanoWrapper:

    @staticmethod
    def shannon_fanonize(node_list):
        list_len = len(node_list)
        if list_len <= 1:
            return
        if list_len == 2:
            node_list[0].code += "0"
            node_list[1].code += "1"
            return

        total_prob = 0
        for item in range(list_len):
            total_prob += node_list[item].prob
        split_prob = 0
        split_index = list_len

        while (split_index >= 0) and (split_prob <= (total_prob - split_prob)):
            split_index -= 1
            split_prob += node_list[split_index].prob

        left_half_diff = split_prob - (total_prob - split_prob)
        right_half_diff = abs(left_half_diff - (2 * node_list[split_index].prob))
        if right_half_diff < left_half_diff:
            split_index += 1

        for item in range(split_index):
            node_list[item].code += "0"

        split_index_temp = split_index
        while split_index_temp < list_len:
            node_list[split_index_temp].code += "1"
            split_index_temp += 1

        if list_len > 0:
            ShannonFanoWrapper.shannon_fanonize(node_list[0:split_index])
            ShannonFanoWrapper.shannon_fanonize(node_list[split_index:])

        return


class ShannonFanoNode:
    def __init__(self, char=None, prob=None, code=None):
        self.char = char
        self.prob = prob  
        self.code = code
