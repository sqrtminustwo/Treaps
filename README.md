# Treaps

This project was created for the **Algorithms and Data Structures 2** course in the academic year **2025–2026** and is shared here for future students of the course. The project received a grade of **3.5/4**. The full report can be found in `extra/verslag.pdf`; however, I would not recommend using it as a reference for the theoretical questions, since many of those are incorrect. The code itself is written efficiently and can be used as a solid example. Below is a short description of each implemented tree.

### Semi-splay Tree

As the name suggests, this structure performs **half-rotations** instead of full splay operations after each insertion, deletion, or search. This approach has been shown to perform better than a traditional splay tree in practice.

### Treap

Standard implementation based on the concept described here:
[https://en.wikipedia.org/wiki/Treap](https://en.wikipedia.org/wiki/Treap)

### LineairFrequencyTreap

A Treap variant where, after each search, the priority of the accessed node is **incremented linearly**. Rotations are then performed if necessary to restore the heap property.

### MyFrequencyTreap

Instead of increasing the priority linearly as in LineairFrequencyTreap, this version increases it **exponentially**, which results in faster upward movement of frequently accessed nodes. More details can be found in the report.

### Custom Treap

For this part of the assignment we had to design our own Treap variant. I chose to combine ideas from the **Treap** and **Semi-splay tree**. A detailed explanation and a comparison with the other trees can be found in the report.
