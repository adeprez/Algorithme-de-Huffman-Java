# Algorithme-de-Huffman-Java
Logiciel permettant de compresser des fichiers grâce à l'algorithme de Huffman

## Module 1 : Analyse du fichier (AnalyseOccurrences.java)
Après lecture du fichier, le nombre d’apparition de chaque élément est compté. Cet algorithme utilise une Map avec pour clef l’élément et pour valeur le nombre de ses apparitions. Cette méthode est plus complexe que l’utilisation d’un tableau ayant pour taille la valeur maximale que peut prendre une valeur, mais elle permet d’utiliser n’importe quel type de données, et ce dans l’optique de garantir la meilleure généricité.


## Module 2 : Construction de l’arbre de Huffman (TableCodage.java, Noeud.java)
L’arbre de Huffman est construit à partir de l’analyse du fichier. Il récupère la liste des noeuds triés depuis l’analyse des occurrences et la parcoure tout en construisant l’arbre selon la méthode de Huffman. L’algorithme est présenté plus en détail dans la partie 4 : analyse de complexité, module 2.


## Module 3 : Décompression de l’arbre de Huffman (TableCodage.java)
A partir de la table de codage, il est possible de décoder tout fichier codé avec la même table. En début de fichier codé, un entier indique le nombre d’éléments contenus dans le fichier, nécessaire pour détecter le dernier élément puisque l’écriture dans un fichier se fait octet par octet, soit 8 bits à la fois. La décompression se fait à partir du noeud de l’arbre contenu dans la table de codage. Lors de la lecture d’un bit de valeur 1, on progresse vers la branche droite, ou vers la branche gauche dans le cas d’un bit à 0. Une fois une feuille atteint, la valeur est ajoutée.
