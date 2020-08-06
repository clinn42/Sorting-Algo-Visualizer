# Sorting Algorithm Visualizer

A Java application used to visualize different sorting algorithms. Randomly generated values represented by bars of respective height are sorted by the available algorithm to visualize and better understand the algorithm/process.
You can adjust size of the Array to be sorted which also determines the speed. FXML used for JavaFX scenes loaded by SceneBuilder.

**Algorithms available:**
* [x] Insertion Sort
* [x] Selection Sort
* [x] Merge Sort
* [x] Quick Sort
* [ ] Radix Sort
* [ ] Heap Sort

### Bugs
* Final sorted colouring effect doesn't always complete. Loading new array still possible program executes normally.
    * Issue lies with KeyFrame/animation duration. Rate variable requires adjusting.
* Load time for Insertion and Selection sort for large sizes takes too long. Program hangs.


Requirements:
* [Java 8](https://openjdk.java.net/install/)
* JavaFX
* [SceneBuilder](https://gluonhq.com/products/scene-builder/)