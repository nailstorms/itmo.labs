class HuffmanWrapper:

    @staticmethod
    def huffman(p):
        # assert(sum(p.values()) == 1.0)

        if(len(p) == 2):
            return dict(zip(p.keys(), ['0', '1']))

        p_prime = p.copy()
        a1, a2 = HuffmanWrapper.lowest_prob_pair(p)
        p1, p2 = p_prime.pop(a1), p_prime.pop(a2)
        p_prime[a1 + a2] = p1 + p2

        c = HuffmanWrapper.huffman(p_prime)
        ca1a2 = c.pop(a1 + a2)
        c[a1], c[a2] = ca1a2 + '0', ca1a2 + '1'

        return c

    @staticmethod
    def lowest_prob_pair(p):
        # assert(len(p) >= 2)
        sorted_p = sorted(p.items(), key=lambda x: x[1])
        return sorted_p[0][0], sorted_p[1][0]
