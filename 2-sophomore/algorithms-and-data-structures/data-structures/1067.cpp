#include <iostream>
#include <map>

using namespace std;

struct structure_tree {
    string name;
    map<string, structure_tree *> child_nodes;
};

void tree_print(structure_tree *tree, int n) {
    for (int i = 0; i < n - 1; ++i) {
        cout << " ";
    }
    if (!tree->name.empty()) {
        cout << tree->name << endl;
    }
    ++n;
    for (auto &j : tree->child_nodes) {
        tree_print(j.second, n);
    }
}

int main() {
    int n;
    cin >> n;

    structure_tree *tree_root = new structure_tree();
    tree_root->name = "";

    for (int i = 0; i < n; i++) {
        string full_path, node_name = "";
        cin >> full_path;

        structure_tree *node = tree_root;

        for (int j = 0; j <= full_path.size(); ++j) {
            if (full_path[j] == '\\' || full_path[j] == '\0') {
                auto tree_iterator = node->child_nodes.find(node_name);
                if (tree_iterator == node->child_nodes.end()) {
                    structure_tree *new_node = new structure_tree();
                    new_node->name = node_name;
                    node->child_nodes[node_name] = new_node;
                    node = node->child_nodes.find(node_name)->second;
                } else {
                    node = tree_iterator->second;
                }
                node_name = "";
            } else node_name += full_path[j];
        }
    }
    tree_print(tree_root, 0);
    return 0;
}
