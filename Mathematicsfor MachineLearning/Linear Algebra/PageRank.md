
# PageRank
In this notebook, you'll build on your knowledge of eigenvectors and eigenvalues by exploring the PageRank algorithm.
The notebook is in two parts, the first is a worksheet to get you up to speed with how the algorithm works - here we will look at a micro-internet with fewer than 10 websites and see what it does and what can go wrong.
The second is an assessment which will test your application of eigentheory to this problem by writing code and calculating the page rank of a large network representing a sub-section of the internet.

## Part 1 - Worksheet
### Introduction

PageRank (developed by Larry Page and Sergey Brin) revolutionized web search by generating a
ranked list of web pages based on the underlying connectivity of the web. The PageRank algorithm is
based on an ideal random web surfer who, when reaching a page, goes to the next page by clicking on a
link. The surfer has equal probability of clicking any link on the page and, when reaching a page with no
links, has equal probability of moving to any other page by typing in its URL. In addition, the surfer may
occasionally choose to type in a random URL instead of following the links on a page. The PageRank is
the ranked order of the pages from the most to the least probable page the surfer will be viewing.



```python
# Before we begin, let's load the libraries.
%pylab notebook
import numpy as np
import numpy.linalg as la
from readonly.PageRankFunctions import *
np.set_printoptions(suppress=True)
```

    Populating the interactive namespace from numpy and matplotlib


### PageRank as a linear algebra problem
Let's imagine a micro-internet, with just 6 websites (**A**vocado, **B**ullseye, **C**atBabel, **D**romeda, **e**Tings, and **F**aceSpace).
Each website links to some of the others, and this forms a network as shown,

![A Micro-Internet](readonly/internet.png "A Micro-Internet")

The design principle of PageRank is that important websites will be linked to by important websites.
This somewhat recursive principle will form the basis of our thinking.

Imagine we have 100 *Procrastinating Pat*s on our micro-internet, each viewing a single website at a time.
Each minute the Pats follow a link on their website to another site on the micro-internet.
After a while, the websites that are most linked to will have more Pats visiting them, and in the long run, each minute for every Pat that leaves a website, another will enter keeping the total numbers of Pats on each website constant.
The PageRank is simply the ranking of websites by how many Pats they have on them at the end of this process.

We represent the number of Pats on each website with the vector,
$$\mathbf{r} = \begin{bmatrix} r_A \\ r_B \\ r_C \\ r_D \\ r_E \\ r_F \end{bmatrix}$$
And say that the number of Pats on each website in minute $i+1$ is related to those at minute $i$ by the matrix transformation

$$ \mathbf{r}^{(i+1)} = L \,\mathbf{r}^{(i)}$$
with the matrix $L$ taking the form,
$$ L = \begin{bmatrix}
L_{A→A} & L_{B→A} & L_{C→A} & L_{D→A} & L_{E→A} & L_{F→A} \\
L_{A→B} & L_{B→B} & L_{C→B} & L_{D→B} & L_{E→B} & L_{F→B} \\
L_{A→C} & L_{B→C} & L_{C→C} & L_{D→C} & L_{E→C} & L_{F→C} \\
L_{A→D} & L_{B→D} & L_{C→D} & L_{D→D} & L_{E→D} & L_{F→D} \\
L_{A→E} & L_{B→E} & L_{C→E} & L_{D→E} & L_{E→E} & L_{F→E} \\
L_{A→F} & L_{B→F} & L_{C→F} & L_{D→F} & L_{E→F} & L_{F→F} \\
\end{bmatrix}
$$
where the columns represent the probability of leaving a website for any other website, and sum to one.
The rows determine how likely you are to enter a website from any other, though these need not add to one.
The long time behaviour of this system is when $ \mathbf{r}^{(i+1)} = \mathbf{r}^{(i)}$, so we'll drop the superscripts here, and that allows us to write,
$$ L \,\mathbf{r} = \mathbf{r}$$

which is an eigenvalue equation for the matrix $L$, with eigenvalue 1 (this is guaranteed by the probabalistic structure of the matrix $L$).

Complete the matrix $L$ below, we've left out the column for which websites the *FaceSpace* website (F) links to.
Remember, this is the probability to click on another website from this one, so each column should add to one (by scaling by the number of links).


```python
# Replace the ??? here with the probability of clicking a link to each website when leaving Website F (FaceSpace).
L = np.array([[0,   1/2, 1/3, 0, 0,   0 ],
              [1/3, 0,   0,   0, 1/2, 0 ],
              [1/3, 1/2, 0,   1, 0,   1/2 ],
              [1/3, 0,   1/3, 0, 1/2, 1/2 ],
              [0,   0,   0,   0, 0,   0 ],
              [0,   0,   1/3, 0, 0,   0 ]])
```

In principle, we could use a linear algebra library, as below, to calculate the eigenvalues and vectors.
And this would work for a small system. But this gets unmanagable for large systems.
And since we only care about the principal eigenvector (the one with the largest eigenvalue, which will be 1 in this case), we can use the *power iteration method* which will scale better, and is faster for large systems.

Use the code below to peek at the PageRank for this micro-internet.


```python
eVals, eVecs = la.eig(L) # Gets the eigenvalues and vectors
#print(eVals)
#print(eVecs)
order = np.absolute(eVals).argsort()[::-1] # Orders them by their eigenvalues
eVals = eVals[order]

eVecs = eVecs[:,order]

r = eVecs[:, 0] # Sets r to be the principal eigenvector
100 * np.real(r / np.sum(r)) # Make this eigenvector sum to one, then multiply by 100 Procrastinating Pats
```




    array([ 16.        ,   5.33333333,  40.        ,  25.33333333,
             0.        ,  13.33333333])



We can see from this list, the number of Procrastinating Pats that we expect to find on each website after long times.
Putting them in order of *popularity* (based on this metric), the PageRank of this micro-internet is:

**C**atBabel, **D**romeda, **A**vocado, **F**aceSpace, **B**ullseye, **e**Tings

Referring back to the micro-internet diagram, is this what you would have expected?
Convince yourself that based on which pages seem important given which others link to them, that this is a sensible ranking.

Let's now try to get the same result using the Power-Iteration method that was covered in the video.
This method will be much better at dealing with large systems.

First let's set up our initial vector, $\mathbf{r}^{(0)}$, so that we have our 100 Procrastinating Pats equally distributed on each of our 6 websites.


```python
r = 100 * np.ones(6) / 6 # Sets up this vector (6 entries of 1/6 × 100 each)
r # Shows it's value
```




    array([ 16.66666667,  16.66666667,  16.66666667,  16.66666667,
            16.66666667,  16.66666667])



Next, let's update the vector to the next minute, with the matrix $L$.
Run the following cell multiple times, until the answer stabilises.


```python
r = L @ r # Apply matrix L to r
r # Show it's value
# Re-run this cell multiple times to converge to the correct answer.
```




    array([ 13.88888889,  13.88888889,  38.88888889,  27.77777778,
             0.        ,   5.55555556])



We can automate applying this matrix multiple times as follows,


```python
r = 100 * np.ones(6) / 6 # Sets up this vector (6 entries of 1/6 × 100 each)
for i in np.arange(100) : # Repeat 100 times
    r = L @ r
r
```




    array([ 16.        ,   5.33333333,  40.        ,  25.33333333,
             0.        ,  13.33333333])



Or even better, we can keep running until we get to the required tolerance.


```python
r = 100 * np.ones(6) / 6 # Sets up this vector (6 entries of 1/6 × 100 each)
lastR = r
r = L @ r
i = 0
while la.norm(lastR - r) > 0.01 :
    lastR = r
    r = L @ r
    i += 1
print(str(i) + " iterations to convergence.")
r
```

    18 iterations to convergence.





    array([ 16.00149917,   5.33252025,  39.99916911,  25.3324738 ,
             0.        ,  13.33433767])



See how the PageRank order is established fairly quickly, and the vector converges on the value we calculated earlier after a few tens of repeats.

Congratulations! You've just calculated your first PageRank!

### Damping Parameter
The system we just studied converged fairly quickly to the correct answer.
Let's consider an extension to our micro-internet where things start to go wrong.

Say a new website is added to the micro-internet: *Geoff's* Website.
This website is linked to by *FaceSpace* and only links to itself.
![An Expanded Micro-Internet](readonly/internet2.png "An Expanded Micro-Internet")

Intuitively, only *FaceSpace*, which is in the bottom half of the page rank, links to this website amongst the two others it links to,
so we might expect *Geoff's* site to have a correspondingly low PageRank score.

Build the new $L$ matrix for the expanded micro-internet, and use Power-Iteration on the Procrastinating Pat vector.
See what happens…


```python
 # We'll call this one L2, to distinguish it from the previous L.
L2 = np.array([[0,   1/2, 1/3, 0, 0,   0, 0 ],
               [1/3, 0,   0,   0, 1/2, 0, 0 ],
               [1/3, 1/2, 0,   1, 0,   1/3, 0 ],
               [1/3, 0,   1/3, 0, 1/2, 1/3, 0 ],
               [0,   0,   0,   0, 0,   0, 0 ],
               [0,   0,   1/3, 0, 0,   0, 0 ],
               [0,   0,   0,   0, 0,   1/3, 1 ]])
```


```python
r = 100 * np.ones(7) / 7 # Sets up this vector (6 entries of 1/6 × 100 each)
lastR = r
r = L2 @ r
i = 0
while la.norm(lastR - r) > 0.01 :
    lastR = r
    r = L2 @ r
    i += 1
print(str(i) + " iterations to convergence.")
r
```

    131 iterations to convergence.





    array([  0.03046998,   0.01064323,   0.07126612,   0.04423198,
             0.        ,   0.02489342,  99.81849527])



That's no good! *Geoff* seems to be taking all the traffic on the micro-internet, and somehow coming at the top of the PageRank.
This behaviour can be understood, because once a Pat get's to *Geoff's* Website, they can't leave, as all links head back to Geoff.

To combat this, we can add a small probability that the Procrastinating Pats don't follow any link on a webpage, but instead visit a website on the micro-internet at random.
We'll say the probability of them following a link is $d$ and the probability of choosing a random website is therefore $1-d$.
We can use a new matrix to work out where the Pat's visit each minute.
$$ M = d \, L + \frac{1-d}{n} \, J $$
where $J$ is an $n\times n$ matrix where every element is one.

If $d$ is one, we have the case we had previously, whereas if $d$ is zero, we will always visit a random webpage and therefore all webpages will be equally likely and equally ranked.
For this extension to work best, $1-d$ should be somewhat small - though we won't go into a discussion about exactly how small.

Let's retry this PageRank with this extension.


```python
d = 0.5 # Feel free to play with this parameter after running the code once.
M = d * L2 + (1-d)/7 * np.ones([7, 7]) # np.ones() is the J matrix, with ones for each entry.
```


```python
r = 100 * np.ones(7) / 7 # Sets up this vector (6 entries of 1/6 × 100 each)
lastR = r
r = M @ r
i = 0
while la.norm(lastR - r) > 0.01 :
    lastR = r
    r = M @ r
    i += 1
print(str(i) + " iterations to convergence.")
r
```

    8 iterations to convergence.





    array([ 13.68217054,  11.20902965,  22.41964343,  16.7593433 ,
             7.14285714,  10.87976354,  17.90719239])



This is certainly better, the PageRank gives sensible numbers for the Procrastinating Pats that end up on each webpage.
This method still predicts Geoff has a high ranking webpage however.
This could be seen as a consequence of using a small network. We could also get around the problem by not counting self-links when producing the L matrix (an if a website has no outgoing links, make it link to all websites equally).
We won't look further down this route, as this is in the realm of improvements to PageRank, rather than eigenproblems.

You are now in a good position, having gained an understanding of PageRank, to produce your own code to calculate the PageRank of a website with thousands of entries.

Good Luck!

## Part 2 - Assessment
In this assessment, you will be asked to produce a function that can calculate the PageRank for an arbitrarily large probability matrix.
This, the final assignment of the course, will give less guidance than previous assessments.
You will be expected to utilise code from earlier in the worksheet and re-purpose it to your needs.

### How to submit
Edit the code in the cell below to complete the assignment.
Once you are finished and happy with it, press the *Submit Assignment* button at the top of this notebook.

Please don't change any of the function names, as these will be checked by the grading script.


```python
# PACKAGE
# Here are the imports again, just in case you need them.
# There is no need to edit or submit this cell.
import numpy as np
import numpy.linalg as la
from readonly.PageRankFunctions import *
np.set_printoptions(suppress=True)
```


```python
# GRADED FUNCTION
# Complete this function to provide the PageRank for an arbitrarily sized internet.
# I.e. the principal eigenvector of the damped system, using the power iteration method.
# (Normalisation doesn't matter here)
# The functions inputs are the linkMatrix, and d the damping parameter - as defined in this worksheet.
def pageRank(linkMatrix, d) :
    n = linkMatrix.shape[0]
    r = 100 * np.ones(n) / n
    M = d * linkMatrix + (1-d)/n * np.ones([n, n])
    
    lastR = r
    r = M @ r
    i = 0
    while la.norm(lastR - r) > 0.01 :
        lastR = r
        r = M @ r
        i += 1
    
    print(str(i) + " iterations to convergence.")
    
    return r

```

## Test your code before submission
To test the code you've written above, run the cell (select the cell above, then press the play button [ ▶| ] or press shift-enter).
You can then use the code below to test out your function.
You don't need to submit this cell; you can edit and run it as much as you like.


```python
# Use the following function to generate internets of different sizes.
generate_internet(5)
```

    /opt/conda/lib/python3.6/site-packages/numpy/core/numeric.py:301: FutureWarning: in the future, full([5, 5], array([0, 1, 2, 3, 4])) will return an array of dtype('int64')
      format(shape, fill_value, array(fill_value).dtype), FutureWarning)





    array([[ 0. ,  0. ,  0. ,  0. ,  0.2],
           [ 0. ,  0. ,  0. ,  0. ,  0.2],
           [ 0. ,  1. ,  0. ,  0. ,  0.2],
           [ 1. ,  0. ,  0.5,  0. ,  0.2],
           [ 0. ,  0. ,  0.5,  1. ,  0.2]])




```python
# Test your PageRank method against the built in "eig" method.
# You should see yours is a lot faster for large internets
L = generate_internet(10)
```

    /opt/conda/lib/python3.6/site-packages/numpy/core/numeric.py:301: FutureWarning: in the future, full([10, 10], array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])) will return an array of dtype('int64')
      format(shape, fill_value, array(fill_value).dtype), FutureWarning)



```python
pageRank(L, 1)
```

    31 iterations to convergence.





    array([  0.00664081,   0.00382851,   0.00218884,   0.00218884,
             0.00218884,   0.00500114,   0.00382851,   0.00218884,
            99.96975686,   0.00218884])




```python
# Do note, this is calculating the eigenvalues of the link matrix, L,
# without any damping. It may give different results that your pageRank function.
# If you wish, you could modify this cell to include damping.
# (There is no credit for this though)
eVals, eVecs = la.eig(L) # Gets the eigenvalues and vectors
order = np.absolute(eVals).argsort()[::-1] # Orders them by their eigenvalues
eVals = eVals[order]
eVecs = eVecs[:,order]

r = eVecs[:, 0]
100 * np.real(r / np.sum(r))
```




    array([  0.00000008,   0.00000005,   0.00000003,   0.00000003,
             0.00000003,   0.00000007,   0.00000005,   0.00000003,
            99.99999958,   0.00000003])




```python
# You may wish to view the PageRank graphically.
# This code will draw a bar chart, for each (numbered) website on the generated internet,
# The height of each bar will be the score in the PageRank.
# Run this code to see the PageRank for each internet you generate.
# Hopefully you should see what you might expect
# - there are a few clusters of important websites, but most on the internet are rubbish!
%pylab notebook
r = pageRank(generate_internet(100), 0.9)
plt.bar(arange(r.shape[0]), r);
```

    Populating the interactive namespace from numpy and matplotlib
    35 iterations to convergence.


    /opt/conda/lib/python3.6/site-packages/numpy/core/numeric.py:301: FutureWarning: in the future, full([100, 100], array([ 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15, 16,
           17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33,
           34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
           51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67,
           68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84,
           85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99])) will return an array of dtype('int64')
      format(shape, fill_value, array(fill_value).dtype), FutureWarning)



    <IPython.core.display.Javascript object>



<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABQAAAAPACAYAAABq3NR5AAAAAXNSR0IArs4c6QAAQABJREFUeAHs3XusZWdZB+BWhhYtICJqgQEHlKaIJhLaCkpRwJDgQKomqBAvja3RmFSrQhgu1Qoa2mIihBgNytUIBiUqdiBykYsSlQEJglqKwIRWK1K8cG8t1HeN+2vWPpwz+5z2W9/tPCt5Z6299zrf5Xn3P/11n7NPOcVBgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAYB8JnLqP9mqrbQmcHsv5ttWSPhHnL7a1PKshQIAAAQIECBAgQIAAAQJDCNwpdvF1q528P843DbErm9iTwIE93e1mAvkEpvDvWL7hjESAAAECBAgQIECAAAECBAhsEDg3Xn/3hnu8PKDAVwy4J1siQIAAAQIECBAgQIAAAQIECBAgQGAl4BOA3gq1BKZf+z1xvOtd7zrl3ve+d3roTIAAAQIECBAgQIAAAQIECGQSuOGGG04577zz0mi3/bd4esJ5fwgIAPdHn1vc5W1/828K/w4ePNjiGq2JAAECBAgQIECAAAECBAiMJHDbf4uPtCl72SzgV4A3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLCAA3G7mDAAECBAgQIECAAAECBAgQIECAQLcCAsBuW2fhBAgQIECAAAECBAgQIECAAAECBDYLHNh8izsIECBAgAABAgQIECBAgEBfAoeOHN1xwcevOLzja14gQIDAiAI+AThiV+2JAAECBAgQIECAAAECBAgQIECAwEpAAOitQIAAAQIECBAgQIAAAQIECBAgQGBgAQHgwM21NQIECBAgQIAAAQIECBAgQIAAAQICQO8BAgQIECBAgAABAgQIECBAgAABAgMLCAAHbq6tESBAgAABAgQIECBAgAABAgQIEBAAeg8QIECAAAECBAgQIECAAAECBAgQGFhAADhwc22NAAECBAgQIECAAAECBAgQIECAgADQe4AAAQIECBAgQIAAAQIECBAgQIDAwAICwIGba2sECBAgQIAAAQIECBAgQIAAAQIEBIDeAwQIECBAgAABAgQIECBAgAABAgQGFhAADtxcWyNAgAABAgQIECBAgAABAgQIECAgAPQeIECAAAECBAgQIECAAAECBAgQIDCwgABw4ObaGgECBAgQIECAAAECBAgQIECAAAEBoPcAAQIECBAgQIAAAQIECBAgQIAAgYEFBIADN9fWCBAgQIAAAQIECBAgQIAAAQIECAgAvQcIECBAgAABAgQIECBAgAABAgQIDCwgABy4ubZGgAABAgQIECBAgAABAgQIECBAQADoPUCAAAECBAgQIECAAAECBAgQIEBgYAEB4MDNtTUCBAgQIECAAAECBAgQIECAAAECAkDvAQIECBAgQIAAAQIECBAgQIAAAQIDCwgAB26urREgQIAAAQIECBAgQIAAAQIECBAQAHoPECBAgAABAgQIECBAgAABAgQIEBhYQAA4cHNtjQABAgQIECBAgAABAgQIECBAgIAA0HuAAAECBAgQIECAAAECBAgQIECAwMACAsCBm2trBAgQIECAAAECBAgQIECAAAECBASA3gMECBAgQIAAAQIECBAgQIAAAQIEBhYQAA7cXFsjQIAAAQIECBAgQIAAAQIECBAgIAD0HiBAgAABAgQIECBAgAABAgQIECAwsIAAcODm2hoBAgQIECBAgAABAgQIECBAgAABAaD3AAECBAgQIECAAAECBAgQIECAAIGBBQSAAzfX1ggQIECAAAECBAgQIECAAAECBAgIAL0HCBAgQIAAAQIECBAgQIAAAQIECAwsIAAcuLm2RoAAAQIECBAgQIAAAQIECBAgQEAA6D1AgAABAgQIECBAgAABAgQIECBAYGABAeDAzbU1AgQIECBAgAABAgQIECBAgAABAgJA7wECBAgQIECAAAECBAgQIECAAAECAwsIAAdurq0RIECAAAECBAgQIECAAAECBAgQEAB6DxAgQIAAAQIECBAgQIAAAQIECBAYWEAAOHBzbY0AAQIECBAgQIAAAQIECBAgQICAANB7gAABAgQIECBAgAABAgQIECBAgMDAAgLAgZtrawQIECBAgAABAgQIECBAgAABAgQEgN4DBAgQIECAAAECBAgQIECAAAECBAYWEAAO3FxbI0CAAAECBAgQIECAAAECBAgQICAA9B4gQIAAAQIECBAgQIAAAQIECBAgMLCAAHDg5toaAQIECBAgQIAAAQIECBAgQIAAAQGg9wABAgQIECBAgAABAgQIECBAgACBgQUEgAM319YIECBAgAABAgQIECBAgAABAgQICAC9BwgQIECAAAECBAgQIECAAAECBAgMLCAAHLi5tkaAAAECBAgQIECAAAECBAgQIEBAAOg9QIAAAQIECBAgQIAAAQIECBAgQGBgAQHgwM21NQIECBAgQIAAAQIECBAgQIAAAQICQO8BAgQIECBAgAABAgQIECBAgAABAgMLCAAHbq6tESBAgAABAgQIECBAgAABAgQIEBAAeg8QIECAAAECBAgQIECAAAECBAgQGFhAADhwc22NAAECBAgQIECAAAECBAgQIECAgADQe4AAAQIECBAgQIAAAQIECBAgQIDAwAICwIGba2sECBAgQIAAAQIECBAgQIAAAQIEBIDeAwQIECBAgAABAgQIECBAgAABAgQGFhAADtxcWyNAgAABAgQIECBAgAABAgQIECAgAPQeIECAAAECBAgQIECAAAECBAgQIDCwgACwv+Z+fSz5CVHPiXpD1I1Rt67q5XHe6/H4+IE/ibo+6qbVeXo8Pe8gQIAAAQIECBAgQIAAAQIECBDoXOBA5+vfj8v/eKZNT+Hvi6Mu2jLefePxVN8f9XtRPx31pSgHAQIECBAgQIAAAQIECBAgQIBAhwI+Adhh02ZL/lhcv3H2eC+Xvx43p/DvvXH95KjzVufp8XRcHPVrJ678Q4AAAQIECBAgQIAAAQIECBAg0KWATwD217bpV3+PrWr6NOChqI9G7eU4K25+6uoH3h3nR0V9fvV4Gvt1UW+POifqaVEvjfqXKAcBAgQIECBAgAABAgQIECBAgEBnAj4B2FnDYrm/EnV11B35VeBL4+dT+HtJXKfwLy5PHJ+Lf6fnp2O67xdOXPmHAAECBAgQIECAAAECBAgQIECgOwEBYHctu8MLPjVGuGA1yjVx/tsdRpye/+Dqten+6eccBAgQIECAAAECBAgQIECAAAECnQkIADtrWIblPiDGuM9qnOnXfE92pNenLwU5dLIbvUaAAAECBAgQIECAAAECBAgQINCmgACwzb4suapvmQ0+fQLwZMf89Qef7EavESBAgAABAgQIECBAgAABAgQItCmQ/g5cm6uzqiUEDs4GvX52vd3ldbMn7ze73s3lfJ7t7j9zuyc9R4AAAQIECBAgQIAAAQIECBAgkFdAAJjXs4fR7jZb5Gdm19tdfnb25F1n17u5nIeHu7nfPQQIECBAgAABAgQIECBAgAABAgsI+BXgBVAbH/Ius/XdPLve7vKm2ZNfObt2SYAAAQIECBAgQIAAAQIECBAg0ImATwB20qiMy/zCbKzTZtfbXZ4+e/Lzs+vdXG76leHpV4CP7WYg9xAgQIAAAQIECBAgQIAAAQIECNx+AQHg7bfr9Sc/PVv4pl/rPWN276ZfF57deuJy098X3Hq/xwQIECBAgAABAgQIECBAgAABAgsI+BXgBVAbH3IezG36oo75p/j8Tb/GG2t5BAgQIECAAAECBAgQIECAAIHtBASA26mM/dw/zbZ39ux6u8v56/+83Q2eI0CAAAECBAgQIECAAAECBAgQaFtAANh2f5ZY3Udj0H9bDfzdGyZ41Or1f43z8Q33epkAAQIECBAgQIAAAQIECBAgQKBBAQFgg01ZeEm3xvh/tppj+oTfw3eYb3o+fQJwun/6OQcBAgQIECBAgAABAgQIECBAgEBnAgLAzhqWabkviHG+uBrrRXH+yi3jTo+n56fjlqjpfgcBAgQIECBAgAABAgQIECBAgECHAr4FuL+mPTKW/M2zZd9rdj09f+Hs8XT58i2Pp4fXRj0/6kjUOVHvjLoy6sNR3xT19KiHRk3HdN+HTlz5hwABAgQIECBAgAABAgQIECBAoDsBAWB3LTvl4ljyT+yw7O+K56eaHy+fP5hdPyuuvz7qJ6OmsO8Po7YeL4knnr31SY8JECBAgAABAgQIECBAgAABAgT6EfArwP30KvdKvxQDXhR1OGr6G3/TF4PcvDpPj78vagobp/scBAgQIECAAAECBAgQIECAAAECnQr4BGB/jbswljxVruP1MdBUDgIECBAgQIAAAQIECBAgQIAAgQEFfAJwwKbaEgECBAgQIECAAAECBAgQIECAAIEkIABMEs4ECBAgQIAAAQIECBAgQIAAAQIEBhQQAA7YVFsiQIAAAQIECBAgQIAAAQIECBAgkAQEgEnCmQABAgQIECBAgAABAgQIECBAgMCAAgLAAZtqSwQIECBAgAABAgQIECBAgAABAgSSgAAwSTgTIECAAAECBAgQIECAAAECBAgQGFBAADhgU22JAAECBAgQIECAAAECBAgQIECAQBIQACYJZwIECBAgQIAAAQIECBAgQIAAAQIDCggAB2yqLREgQIAAAQIECBAgQIAAAQIECBBIAgLAJOFMgAABAgQIECBAgAABAgQIECBAYEABAeCATbUlAgQIECBAgAABAgQIECBAgAABAklAAJgknAkQIECAAAECBAgQIECAAAECBAgMKCAAHLCptkSAAAECBAgQIECAAAECBAgQIEAgCQgAk4QzAQIECBAgQIAAAQIECBAgQIAAgQEFBIADNtWWCBAgQIAAAQIECBAgQIAAAQIECCQBAWCScCZAgAABAgQIECBAgAABAgQIECAwoIAAcMCm2hIBAgQIECBAgAABAgQIECBAgACBJCAATBLOBAgQIECAAAECBAgQIECAAAECBAYUEAAO2FRbIkCAAAECBAgQIECAAAECBAgQIJAEBIBJwpkAAQIECBAgQIAAAQIECBAgQIDAgAICwAGbaksECBAgQIAAAQIECBAgQIAAAQIEkoAAMEk4EyBAgAABAgQIECBAgAABAgQIEBhQQAA4YFNtiQABAgQIECBAgAABAgQIECBAgEASEAAmCWcCBAgQIECAAAECBAgQIECAAAECAwoIAAdsqi0RIECAAAECBAgQIECAAAECBAgQSAICwCThTIAAAQIECBAgQIAAAQIECBAgQGBAAQHggE21JQIECBAgQIAAAQIECBAgQIAAAQJJQACYJJwJECBAgAABAgQIECBAgAABAgQIDCggABywqbZEgAABAgQIECBAgAABAgQIECBAIAkIAJOEMwECBAgQIECAAAECBAgQIECAAIEBBQSAAzbVlggQIECAAAECBAgQIECAAAECBAgkAQFgknAmQIAAAQIECBAgQIAAAQIECBAgMKCAAHDAptoSAQIECBAgQIAAAQIECBAgQIAAgSQgAEwSzgQIECBAgAABAgQIECBAgAABAgQGFBAADthUWyJAgAABAgQIECBAgAABAgQIECCQBASAScKZAAECBAgQIECAAAECBAgQIECAwIACAsABm2pLBAgQIECAAAECBAgQIECAAAECBJKAADBJOBMgQIAAAQIECBAgQIAAAQIECBAYUEAAOGBTbYkAAQIECBAgQIAAAQIECBAgQIBAEhAAJglnAgQIECBAgAABAgQIECBAgAABAgMKCAAHbKotESBAgAABAgQIECBAgAABAgQIEEgCAsAk4UyAAAECBAgQIECAAAECBAgQIEBgQAEB4IBNtSUCBAgQIECAAAECBAgQIECAAAECSUAAmCScCRAgQIAAAQIECBAgQIAAAQIECAwoIAAcsKm2RIAAAQIECBAgQIAAAQIECBAgQCAJCACThDMBAgQIECBAgAABAgQIECBAgACBAQUEgAM21ZYIECBAgAABAgQIECBAgAABAgQIJAEBYJJwJkCAAAECBAgQIECAAAECBAgQIDCggABwwKbaEgECBAgQIECAAAECBAgQIECAAIEkIABMEs4ECBAgQIAAAQIECBAgQIAAAQIEBhQQAA7YVFsiQIAAAQIECBAgQIAAAQIECBAgkAQEgEnCmQABAgQIECBAgAABAgQIECBAgMCAAgLAAZtqSwQIECBAgAABAgQIECBAgAABAgSSgAAwSTgTIECAAAECBAgQIECAAAECBAgQGFBAADhgU22JAAECBAgQIECAAAECBAgQIECAQBIQACYJZwIECBAgQIAAAQIECBAgQIAAAQIDCggAB2yqLREgQIAAAQIECBAgQIAAAQIECBBIAgLAJOFMgAABAgQIECBAgAABAgQIECBAYEABAeCATbUlAgQIECBAgAABAgQIECBAgAABAklAAJgknAkQIECAAAECBAgQIECAAAECBAgMKCAAHLCptkSAAAECBAgQIECAAAECBAgQIEAgCQgAk4QzAQIECBAgQIAAAQIECBAgQIAAgQEFBIADNtWWCBAgQIAAAQIECBAgQIAAAQIECCQBAWCScCZAgAABAgQIECBAgAABAgQIECAwoIAAcMCm2hIBAgQIECBAgAABAgQIECBAgACBJCAATBLOBAgQIECAAAECBAgQIECAAAECBAYUEAAO2FRbIkCAAAECBAgQIECAAAECBAgQIJAEBIBJwpkAAQIECBAgQIAAAQIECBAgQIDAgAICwAGbaksECBAgQIAAAQIECBAgQIAAAQIEkoAAMEk4EyBAgAABAgQIECBAgAABAgQIEBhQQAA4YFNtiQABAgQIECBAgAABAgQIECBAgEASEAAmCWcCBAgQIECAAAECBAgQIECAAAECAwoIAAdsqi0RIECAAAECBAgQIECAAAECBAgQSAICwCThTIAAAQIECBAgQIAAAQIECBAgQGBAAQHggE21JQIECBAgQIAAAQIECBAgQIAAAQJJQACYJJwJECBAgAABAgQIECBAgAABAgQIDCggABywqbZEgAABAgQIECBAgAABAgQIECBAIAkIAJOEMwECBAgQIECAAAECBAgQIECAAIEBBQSAAzbVlggQIECAAAECBAgQIECAAAECBAgkAQFgknAmQIAAAQIECBAgQIAAAQIECBAgMKCAAHDAptoSAQIECBAgQIAAAQIECBAgQIAAgSQgAEwSzgQIECBAgAABAgQIECBAgAABAgQGFBAADthUWyJAgAABAgQIECBAgAABAgQIECCQBASAScKZAAECBAgQIECAAAECBAgQIECAwIACAsABm2pLBAgQIECAAAECBAgQIECAAAECBJKAADBJOBMgQIAAAQIECBAgQIAAAQIECBAYUEAAOGBTbYkAAQIECBAgQIAAAQIECBAgQIBAEhAAJglnAgQIECBAgAABAgQIECBAgAABAgMKCAAHbKotESBAgAABAgQIECBAgAABAgQIEEgCAsAk4UyAAAECBAgQIECAAAECBAgQIEBgQAEB4IBNtSUCBAgQIECAAAECBAgQIECAAAECSUAAmCScCRAgQIAAAQIECBAgQIAAAQIECAwoIAAcsKm2RIAAAQIECBAgQIAAAQIECBAg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hidAgAABAgQIECBAgAABAgQIECBQU0AAWFPf3AQIECBAgAABAgQIECBAgAABAgQWFhAALgxseAIECBAgQIAAAQIECBAgQIAAAQI1BQSANfXNTYAAAQIECBAgQIAAAQIECBAgQGBhAQHgwsCGJ0CAAAECBAgQIECAAAECBAgQIFBTQABYU9/cBAgQIECAAAECBAgQIECAAAECBBYWEAAuDGx4AgQIECBAgAABAgQIECBAgAABAjUFBIA19c1NgAABAgQIECBAgAABAgQIECBAYGEBAeDCwIYnQIAAAQIECBAgQIAAAQIECBAgUFNAAFhT39wECBAgQIAAAQIECBAgQIAAAQIEFhYQAC4MbHgCBAgQIECAAAECBAgQIECAAAECNQUEgDX1zU2AAAECBAgQIECAAAECBAgQIEBgYQEB4MLAhidAgAABAgQIECBAgAABAgQIECBQU0AAWFPf3AQIECBAgAABAgQIECBAgAABAgQWFhAALgxseAIECBAgQIAAAQIECBAgQIAAAQI1BQSANfXNTYAAAQIECBAgQIAAAQIECBAgQGBhAQHgwsCGJ0CAAAECBAgQIECAAAECBAgQIFBTQABYU9/cBAgQIECAAAECBAgQIECAAAECBBYWEAAuDGx4AgQIECBAgAABAgQIECBAgAABAjUFBIA19c1NgAABAgQIECBAgAABAgQIECBAYGEBAeDCwIYnQIAAAQIECBAgQIAAAQIECBAgUFNAAFhT39wECBAgQIAAAQIECBAgQIAAAQIEFhYQAC4MbHgCBAgQIECAAAECBAgQIECAAAECNQUEgDX1zU2AAAECBAgQIECAAAECBAgQIEBgYQEB4MLAhidAgAABAgQIECBAgAABAgQIECBQU0AAWFPf3AQIECBAgAABAgQIECBAgAABAgQWFhAALgxseAIECBAgQIAAAQIECBAgQIAAAQI1BQSANfXNTYAAAQIECBAgQIAAAQIECBAgQGBhAQHgwsCGJ0CAAAECBAgQIECAAAECBAgQIFBTQABYU9/cBAgQIECAAAECBAgQIECAAAECBBYWEAAuDGx4AgQIECBAgAABAgQIECBAgAABAjUFBIA19c1NgAABAgQIECBAgAABAgQIECBAYGEBAeDCwIYnQIAAAQIECBAgQIAAAQIECBAgUFNAAFhT39wECBAgQIAAAQIECBAgQIAAAQIEFhYQAC4MbHgCBAgQIECAAAECBAgQIECAAAECNQUEgDX1zU2AAAECBAgQIECAAAECBAgQIEBgYQEB4MLAhidAgAABAgQIECBAgAABAgQIECBQU0AAWFPf3AQIECBAgAABAgQIECBAgAABAgQWFhAALgxseAIECBAgQIAAAQIECBAgQIAAAQI1BQSANfXNTYAAAQIECBAgQIAAAQIECBAgQGBhAQHgwsCGJ0CAAAECBAgQIECAAAECBAgQIFBTQABYU9/cBAgQIECAAAECBAgQIECAAAECBBYWEAAuDGx4AgQIECBAgAABAgQIECBAgAABAjUFBIA19c1NgAABAgQIECBAgAABAgQIECBAYGEBAeDCwIYnQIAAAQIECBAgQIAAAQIECBAgUFNAAFhT39wECBAgQIAAAQIECBAgQIAAAQIEFhYQAC4MbHgCBAgQIECAAAECBAgQIECAAAECNQUEgDX1zU2AAAECBAgQIECAAAECBAgQIEBgYQEB4MLAhidAgAABAgQIECBAgAABAgQIECBQU0AAWFPf3AQIECBAgAABAgQIECBAgAABAgQWFhAALgxseAIECBAgQIAAAQIECBAgQIAAAQI1BQSANfXNTYAAAQIECBAgQIAAAQIECBAgQGBhAQHgwsCGJ0CAAAECBAgQIECAAAECBAgQIFBTQABYU9/cBAgQIECAAAECBAgQIECAAAECBBYWEAAuDGx4AgQIECBAgAABAgQIECBAgAABAjUFBIA19c1NgAABAgQIECBAgAABAgQIECBAYGEBAeDCwIYnQIAAAQIECBAgQIAAAQIECBAgUFNAAFhT39wECBAgQIAAAQIECBAgQIAAAQIEFhYQAC4MbHgCBAgQIECAAAECBAgQIECAAAECNQUEgDX1zU2AAAECBAgQIECAAAECBAgQIEBgYQEB4MLAhidAgAABAgQIECBAgAABAgQIECBQU0AAWFPf3AQIECBAgAABAgQIECBAgAABAgQWFhAALgxseAIECBAgQIAAAQIECBAgQIAAAQI1BQSANfXNTYAAAQIECBAgQIAAAQIECBAgQGBhAQHgwsCGJ0CAAAECBAgQIECAAAECBAgQIFBTQABYU9/cBAgQIECAAAECBAgQIECAAAECBBYWEAAuDGx4AgQIECBAgAABAgQIECBAgAABAjUFDtSc3NwECBAgQIAAAQIECBAgQKBlgUNHju64vONXHN7xNS8QIECgJQGfAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCQgAGypG9ZCgAABAgQIECBAgAABAgQIECBAILOAADAzqOEIECBAgAABAgQIECBAgAABAgQItCRwoKXFWEtVgfvH7BdFHY76xqi7RX0i6njUW6NeE/WBKAcBAgQIECBAgAABAgQIECBAgEBHAgLAjpq14FIvibGfF3XGljkOxuOpHhl196hLoxwECBAgQIAAAQIECBAgQIAAAQIdCQgAO2rWQkt9doz73NXY18b5d6OORf1P1NdGPTTqB6K+FOUgQIAAAQIECBAgQIAAAQIECBDoTEAA2FnDMi/3sTFeCv9eGdcXR/3vljneEo9/I+q0Lc97SIAAAQIECBAgQIAAAQIECBAg0IGAALCDJi20xOkLYH57Nfb74jz9/b9bVo+3O9283ZOeI0CAAAECBAgQIECAAAECBAgQaFvAtwC33Z8lV/e4GPxBqwmujPPJwr8l12FsAgQIECBAgAABAgQIECBAgACBBQUEgAviNj70k1bruzXOV8/Wes+4noLB6ewgQIAAAQIECBAgQIAAAQIECBDoXEAA2HkD78DyH7762eNx/nTUU6LeH/XJqOnLQKbzB6OeGnV6lIMAAQIECBAgQIAAAQIECBAgQKBDAX8DsMOmZVjyFPyevRrnxji/MOrnVo/np7PiwfOjpm8BPhz131G7PQ5uuPHMDa97mQABAgQIECBAgAABAgQIECBAIIOAADADYodDfHWsOX3689vi+tyoG6KeFvX6qC9ETc9Nfxtw+qTgd0a9NOoHo3Z7XLfbG91HgAABAgQIECBAgAABAgQIECCwnEAKgZabwcgtCpwxW9Rd4vpzUY+O+oOo/4r6fNQ7oh4TNX1D8HRMnwL8jhNX/iFAgAABAgQIECBAgAABAgQIEOhGwCcAu2lV1oVOn/CbH78XD6a/97f1mILAZ0WlLwn54bj+u6037fD4fjs8n56efgX4WHrgTIAAAQIECBAgQIAAAQIECBAgsIyAAHAZ19ZHnb70Y368cf5gy/Vb4vEtUdN7Zfq14N0e1+/2RvftLHDoyNEdXzx+xfRnGR0ECBAgQIAAAQIECBAgQIAAgZML+BXgk/uM+upNsbFPzDZ3sr/XN31acPqikOn4uv8/+ZcAAQIECBAgQIAAAQIECBAgQKAXAQFgL53Kv85/nA15p9n1dpfp9emTgA4CBAgQIECAAAECBAgQIECAAIGOBASAHTUr81LfMRvvgbPrrZd3jyfutXryX7e+6DEBAgQIECBAgAABAgQIECBAgEDbAgLAtvuz5OpeOxt8+obfnY7ptVNXL/7VTjd5ngABAgQIECBAgAABAgQIECBAoE0BAWCbfSmxqn+ISd6wmujJcX7sNpNO39T7a6vnb47zy7a5x1MECBAgQIAAAQIECBAgQIAAAQINCwgAG25OgaVdGnP8d9T0Prg66nlR50edE/WzUceiDkZNx2VRfgX4BIV/CBAgQIAAAQIECBAgQIAAAQL9CBzoZ6lWuoDAtTHmE6P+OOoboo6sKk63HbfG1a9HXXXbMy4IECBAgAABAgQIECBAgAABAgS6ERAAdtOqxRb61zHyQ6Iuifr+qAdEnRZ1Q9Tbol4U9d4oBwECBAgQIECAAAECBAgQIECAQIcCAsAOm7bAkj8ZY16+qgWGNyQBAgQIECBAgAABAgQIECBAgEAtAX8DsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQEgAWQTUGAAAECBAgQIECAAAECBAgQIECgloAAsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQEgAWQTUGAAAECBAgQIECAAAECBAgQIECgloAAsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQEgAWQTUGAAAECBAgQIECAAAECBAgQIECgloAAsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQEgAWQTUGAAAECBAgQIECAAAECBAgQIECgloAAsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQEgAWQTUGAAAECBAgQIECAAAECBAgQIECgloAAsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQEgAWQTUGAAAECBAgQIECAAAECBAgQIECgloAAsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQEgAWQTUGAAAECBAgQIECAAAECBAgQIECgloAAsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQEgAWQTUGAAAECBAgQIECAAAECBAgQIECgloAAsJa8eQkQIECAAAECBAgQIECAAAECBAgUEBAAFkA2BQECBAgQIECAAAECBAgQIECAAIFaAgLAWvLmJUCAAAECBAgQIECAAAECBAgQIFBAQABYANkUBAgQIECAAAECBAgQIECAAAECBGoJCABryZuXAAECBAgQIECAAAECBAgQIECAQAEBAWABZFMQIECAAAECBAgQIECAAAECBAgQqCUgAKwlb14CBAgQIECAAAECBAgQIECAAAECBQQOFJjDFAQIECBAgAABAgQIENizwJDEtakAAD4VSURBVKEjR3f8meNXHN7xNS8QIECAAAEC6wI+Abju4REBAgQIECBAgAABAgQIECBAgACBoQQEgEO102YIECBAgAABAgQIECBAgAABAgQIrAsIANc9PCJAgAABAgQIECBAgAABAgQIECAwlIAAcKh22gwBAgQIECBAgAABAgQIECBAgACBdQEB4LqHRwQIECBAgAABAgQIECBAgAABAgSGEhAADtVOmyFAgAABAgQIECBAgAABAgQIECCwLiAAXPfwiAABAgQIECBAgAABAgQIECBAgMBQAgLAodppMwQIECBAgAABAgQIECBAgAABAgTWBQSA6x4eESBAgAABAgQIECBAgAABAgQIEBhKQAA4VDtthgABAgQIECBAgAABAgQIECBAgMC6gABw3cMjAgQIECBAgAABAgQIECBAgAABAkMJCACHaqfNECBAgAABAgQIECBAgAABAgQIEFgXEACue3hEgAABAgQIECBAgAABAgQIECBAYCgBAeBQ7bQZAgQIECBAgAABAgQIECBAgAABAusCAsB1D48IECBAgAABAgQIECBAgAABAgQIDCUgAByqnTZDgAABAgQIECBAgAABAgQIECBAYF1AALju4REBAgQIECBAgAABAgQIECBAgACBoQQEgEO102YIECBAgAABAgQIECBAgAABAgQIrAsIANc9PCJAgAABAgQIECBAgAABAgQIECAwlIAAcKh22gwBAgQIECBAgAABAgQIECBAgACBdQEB4LqHRwQIECBAgAABAgQIECBAgAABAgSGEhAADtVOmyFAgAABAgQIECBAgAABAgQIECCwLiAAXPfwiAABAgQIECBAgAABAgQIECBAgMBQAgLAodppMwQIECBAgAABAgQIECBAgAABAgTWBQSA6x4eESBAgAABAgQIECBAgAABAgQIEBhKQAA4VDtthgABAgQIECBAgAABAgQIECBAgMC6gABw3cMjAgQIECBAgAABAgQIECBAgAABAkMJCACHaqfNECBAgAABAgQIECBAgAABAgQIEFgXEACue3hEgAABAgQIECBAgAABAgQIECBAYCgBAeBQ7bQZAgQIECBAgAABAgQIECBAgAABAusCAsB1D48IECBAgAABAgQIECBAgAABAgQIDCUgAByqnTZDgAABAgQIECBAgAABAgQIECBAYF1AALju4REBAgQIECBAgAABAgQIECBAgACBoQQEgEO1M8tmroxRbp3V92QZ1SAECBAgQIAAAQIECBAgQIAAAQJVBASAVdibnfTbY2W/2OzqLIwAAQIECBAgQIAAAQIECBAgQGDPAgLAPZMN+wPTe+HFUQei/mPYXdoYAQIECBAgQIAAAQIECBAgQGCfCQgA91nDT7Ldn4vXzo26JuolJ7nPSwQIECBAgAABAgQIECBAgAABAh0JCAA7ataCS71/jP3c1fg/E+ebF5zL0AQIECBAgAABAgQIECBAgAABAgUFBIAFsRue6rdibXeNekXU2xtep6URIECAAAECBAgQIECAAAECBAjsUUAAuEewAW//odjTE6L+M+qpA+7PlggQIECAAAECBAgQIECAAAEC+1pAALiv23/KPWL7L1wRPD3ON+5vDrsnQIAAAQIECBAgQIAAAQIECIwnMH3jq2P/ClwVWz8z6p1Rub/44+AG1mleBwECBAgQIECAAAECBAgQIECAwMICAsCFgRse/vxY28VRt0RNX/xxa1TO47qcgxmLAAECBAgQIECAAAECBAgQIEDg9gn4FeDb59b7T50WG3hx1KlRvxn1gSgHAQIECBAgQIAAAQIECBAgQIDAgAI+AThgU3expWfGPWdHfSzqV3dx/+255X4bfmj6FeBjG+7xMgECBAgQIECAAAECBAgQIECAwB0UEADeQcAOf3wK/p6xWvclcf7sQnu4fqFxDUuAAAECBAgQIECAAAECBAgQILAHAQHgHrAGufUXYh/TrwB/JOqron4kauvxrbMnHhPX6Qs7/jyulwoMZ1O6JECAAAECBAgQIECAAAECBAgQyCUgAMwl2c84p6+W+sA4v3oXy75sds8D4loAOANxSYAAAQIECBAgQIAAAQIECBBoXcCXgLTeIesjQIAAAQIECBAgQIAAAQIECBAgcAcEBIB3AK/TH70w1j19++/Jav7FII+e3Xs8rh0ECBAgQIAAAQIECBAgQIAAAQIdCQgAO2qWpRIgQIAAAQIECBAgQIAAAQIECBDYq4AAcK9i7idAgAABAgQIECBAgAABAgQIECDQkYAAsKNmWSoBAgQIECBAgAABAgQIECBAgACBvQoIAPcq5n4CBAgQIECAAAECBAgQIECAAAECHQkIADtqVsGlXh5zpS8JeVvBeU1FgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEhAAttQNayFAgAABAgQIECBAgAABAgQIECCQWUAAmBnUcAQIECBAgAABAgQIECBAgAABAgRaEjjQ0mKshQABAgQIECDQk8ChI0d3XO7xKw7v+JoXCBAgQIAAAQIECJQU8AnAktrmIkCAAAECBAgQIECAAAECBAgQIFBYQABYGNx0BAgQIECAAAECBAgQIECAAAECBEoKCABLapuLAAECBAgQIECAAAECBAgQIECAQGEBAWBhcNMRIECAAAECBAgQIECAAAECBAgQKCkgACypbS4CBAgQIECAAAECBAgQIECAAAEChQUEgIXBTUeAAAECBAgQIECAAAECBAgQIECgpIAAsKS2uQgQIECAAAECBAgQIECAAAECBAgUFhAAFgY3HQECBAgQIECAAAECBAgQIECAAIGSAgLAktrmIkCAAAECBAgQIECAAAECBAgQIFBYQABYGNx0BAgQIECAAAECBAgQIECAAAECBEoKCABLapuLAAECBAgQIECAAAECBAgQIECAQGEBAWBhcNMRIECAAAECBAgQIECAAAECBAgQKCkgACypbS4CBAgQIECAAAECBAgQIECAAAEChQUEgIXBTUeAAAECBAgQIECAAAECBAgQIECgpIAAsKS2uQgQIECAAAECBAgQIECAAAECBAgUFhAAFgY3HQECBAgQIECAAAECBAgQIECAAIGSAgLAktrmIkCAAAECBAgQIECAAAECBAgQIFBYQABYGNx0BAgQIECAAAECBAgQIECAAAECBEoKCABLapuLAAECBAgQIECAAAECBAgQIECAQGEBAWBhcNMRIECAAAECBAgQIECAAAECBAgQKCkgACypbS4CBAgQIECAAAECBAgQIECAAAEChQUEgIXBTUeAAAECBAgQIECAAAECBAgQIECgpIAAsKS2uQgQIECAAAECBAgQIECAAAECBAgUFjhQeD7TESBAgACB4QQOHTm6456OX3F4x9e8QIAAAQIECBAgQIAAgRICPgFYQtkcBAgQIECAAAECBAgQIECAAAECBCoJCAArwZuWAAECBAgQIECAAAECBAgQIECAQAkBAWAJZXMQIECAAAECBAgQIECAAAECBAgQqCQgAKwEb1oCBAgQIECAAAECBAgQIECAAAECJQQEgCWUzUGAAAECBAgQIECAAAECBAgQIECgkoAAsBK8aQkQIECAAAECBAgQIECAAAECBAiUEDhQYhJzECBAYASBQ0eO7riN41cc3vE1LxAgQIAAAQIECBAgQIAAgZoCAsCa+uYmQKCIgOCuCLNJCBAgQIAAAQIECBAgQKBRAQFgo42xLAIECBAgQIDAXgX8D4+9irmfAAECBAgQILA/BPwNwP3RZ7skQIAAAQIECBAgQIAAAQIECBDYpwICwH3aeNsmQIAAAQIECBAgQIAAAQIECBDYHwICwP3RZ7skQIAAAQIECBAgQIAAAQIECBDYpwICwH3aeNsmQIAAAQIECBAgQIAAAQIECBDYHwICwP3R5+12eU48+ctRb4y6PuqmqM9EXRv1sqhHRjkIECBAgAABAgQIECBAgAABAgQ6F/AtwJ038HYu/x3xc+dv87OnxXMPWtWFcX5l1E9F3RzlIECAAAECBAgQIECAAAECBAgQ6FBAANhh0zIs+T6rMf4tzn8U9VdRH4u6U9Qjon4p6r5RPx5156inRDkIECBQRODQkaM7znP8isM7vuYFAgQIECBAgAABAgQIENheQAC4vcvoz14TG3xm1Gujvrhls38bj38/6p1RZ0U9Oep3oqZPDToIECBAgAABAgQIECBAgAABAgQ6E/iKztZruXkEnhDDvCZqa/j3f+3dC7AkVXkAYEBEAV9EVEohLIoUKCQSAcUXKxoqZrVM4oukEjVqUjFVPoiv1Zi4mgRXLY1YwTJGdLWMllbFN0mBDxQhWiJGowKiMUuAoICi8hAQJP/P9iHtONMzc2/PTPfMd6r+22e6e845/Z1z53af6ZlbSr8yMnkXYElPLhlLAgQIECBAgAABAgQIECBAgACBfgmYAOxXf82ztWfUKrtfLS9LgAABAgQIECBAgAABAgQIECDQIwETgD3qrDk39Q61+kbdKVjbRZYAAQIECBAgQIAAAQIECBAgQKCLAr4DsIu90o02HVNrxvm1vCwBAgQIEOi9gH820/sudAAECBAgQIAAAQJTCJgAnAJrhXbNO0M31443vy9w2rTvmCfsM2a7zQQIzEnARMicoFVDgAABAgQIECBAgACBBQmYAFwQfMerPSHad1TVxg/F8tw1tPfiNTzHUwgQIECAAAECBAgQIECAAAECBFoW8B2ALYMuQXH50d+t1XFcHsvnLsExOQQCBAgQIECAAAECBAgQIECAwMoKuANwZbt+6IE/MNZ+OCLHxfURT4nIScC1pP3GPCk/AnzOmH1sJkCAAAECBAgQIECAAAECBAgQWKeACcB1Ai7R0w+IYzk9Yq+I/K+/x0ecGbHWdMlan+h5BAgQIDBawHc2jraxhQABAgQIECBAgACB4QI+AjzcZdXW3jsO+FMRubwl4lkRH42QCBAgQIAAAQIECBAgQIAAAQIEei5gArDnHdhC8/eOMj4Zcd+qrOfF8j1V3oIAAQIECBAgQIAAAQIECBAgQKDnAiYAe96B62z+XeP5p0U8oCpncyxPrvIWBAgQIECAAAECBAgQIECAAAECSyBgAnAJOnGNh7BHPO/UiN+onv93sXxdlbcgQIAAAQIECBAgQIAAAQIECBBYEgETgEvSkVMexm6xf/6334dXzzsplq+s8hYECBAgQIAAAQIECBAgQIAAAQJLJOC/AC9RZ05xKO+PfY+r9v9MLE+JOLR6PGxxY6y8cNgG6wgQIECAAAECBAgQIECAAAECBLotYAKw2/0zq9b9Xq3gYyP/n7XHw7IXxcoNwzZYR4AAAQIECBAgQIAAAQIECBAg0G0BHwHudv9oHQECBAgQIECAAAECBAgQIECAAIF1CbgDcF18vX3yzr1tuYYTIECAAAECBAgQIECAAAECBAhMJeAOwKm47EyAAAECBAgQIECAAAECBAgQIECgXwImAPvVX1pLgAABAgQIECBAgAABAgQIECBAYCoBHwGeisvOBAgQIECAAIHlF9iw+dSRB7l966aR22wgQIAAAQIECBDopoA7ALvZL1pFgAABAgQIECBAgAABAgQIECBAoBUBE4CtMCqEAAECBAgQIECAAAECBAgQIECAQDcFTAB2s1+0igABAgQIECBAgAABAgQIECBAgEArAiYAW2FUCAECBAgQIECAAAECBAgQIECAAIFuCpgA7Ga/aBUBAgQIECBAgAABAgQIECBAgACBVgRMALbCqBACBAgQIECAAAECBAgQIECAAAEC3RQwAdjNftEqAgQIECBAgAABAgQIECBAgAABAq0ImABshVEhBAgQIECAAAECBAgQIECAAAECBLopYAKwm/2iVQQIECBAgAABAgQIECBAgAABAgRaETAB2AqjQggQIECAAAECBAgQIECAAAECBAh0U2DXbjZLqwgQINBPgQ2bTx3Z8O1bN43cZgMBAgQIECBAgAABAgQIEJiVgDsAZyWrXAIECBAgQIAAAQIECBAgQIAAAQIdEDAB2IFO0AQCBAgQIECAAAECBAgQIECAAAECsxIwATgrWeUSIECAAAECBAgQIECAAAECBAgQ6ICACcAOdIImECBAgAABAgQIECBAgAABAgQIEJiVgAnAWckqlwABAgQIECBAgAABAgQIECBAgEAHBEwAdqATNIEAAQIECBAgQIAAAQIECBAgQIDArAR2nVXByiVAgAABAgQIEGhPYMPmU0cWtn3rppHbbCBAgAABAgQIECDgDkBjgAABAgQIECBAgAABAgQIECBAgMASC5gAXOLOdWgECBAgQIAAAQIECBAgQIAAAQIETAAaAwQIECBAgAABAgQIECBAgAABAgSWWMB3AC5x5zo0AgQIECBAgMCggO8SHBTxmAABAgQIECCw/ALuAFz+PnaEBAgQIECAAAECBAgQIECAAAECKyxgAnCFO9+hEyBAgAABAgQIECBAgAABAgQILL+ACcDl72NHSIAAAQIECBAgQIAAAQIECBAgsMICvgNwhTvfoRMgQIAAAQIECBAgQKAtAd8x2pakcggQINC+gDsA2zdVIgECBAgQIECAAAECBAgQIECAAIHOCLgDsDNdoSEECBAgQIAAgdUTcMfQ6vW5IyZAgAABAgTmL+AOwPmbq5EAAQIECBAgQIAAAQIECBAgQIDA3ATcATg3ahURIECgXQF3zbTrqTQCBAgQIECAAAECBAgsq4A7AJe1Zx0XAQIECBAgQIAAAQIECBAgQIAAgRAwAWgYECBAgAABAgQIECBAgAABAgQIEFhiAR8BXuLOdWgECBAgQIAAgWUQ8JUHy9CLjoEAAQIECBBYpIA7ABepr24CBAgQIECAAAECBAgQIECAAAECMxYwAThjYMUTIECAAAECBAgQIECAAAECBAgQWKSACcBF6qubAAECBAgQIECAAAECBAgQIECAwIwFTADOGFjxBAgQIECAAAECBAgQIECAAAECBBYpYAJwkfrqJkCAAAECBAgQIECAAAECBAgQIDBjAROAMwZWPAECBAgQIECAAAECBAgQIECAAIFFCuy6yMrVTYAAAQIEui6wYfOpI5u4feumkdtsIECAAAECBAgQIECAQFcE3AHYlZ7QDgIECBAgQIAAAQIECBAgQIAAAQIzEDABOANURRIgQIAAAQIECBAgQIAAAQIECBDoioCPAHelJ7SDAAECBOYu4OO9cydXIQECBAgQIECAAAECCxAwAbgAdFUSIECAAAECBAgQIEBgvQLeyFqvoOcTIEBgdQRMAK5OXztSAgQIzFTARchMeRVOgAABAgQIECBAgACBNQv4DsA103kiAQIECBAgQIAAAQIECBAgQIAAge4LuAOw+32khQQIECBAoNcCTXeH5oFt37qp18en8QQIECBAgAABAgS6LmACsOs9pH0ECBAgQIAAAQIECBAg0HuBpjfEvBnW++51AAQ6L2ACsPNdpIEECBAgQKB9gaaLkKzNhchOOzUZ8Wl/TK63RP21XkHPJ0CAAAECBJZZwHcALnPvOjYCBAgQIECAAAECBAgQIECAAIGVFzABuPJDAAABAgQIECBAgAABAgQIECBAgMAyC/gI8DL3rmMjQIAAAQIECBAgQGClBXw8fqW738ETIEDgNgF3AN5GIUOAAAECBAgQIECAAAECBAgQIEBg+QTcAbh8feqICBAgQIAAAQIECBDosIC78jrcOZpGgACBJRVwB+CSdqzDIkCAAAECBAgQIECAAAECBAgQIJAC7gA0DggQIECgdwLunOhdl2kwAQIECBAgQIAAAQILFHAH4ALxVU2AAAECBAgQIECAAAECBAgQIEBg1gLuAJy1sPIJECBAYCECfbxLsI9tXkjnqpQAAQIECBAgQIAAgakE3AE4FZedCRAgQIAAAQIECBAgQIAAAQIECPRLwB2A/eovrSVAgAABAgQI9EbAXa296SoNJUCAAAECBJZcwB2AS97BDo8AAQIECBAgQIAAAQIECBAgQGC1BUwArnb/O3oCBAgQIECAAAECBAgQIECAAIElFzABuOQd7PAIECBAgAABAgQIECBAgAABAgRWW8B3AK52/zt6AgQIECAwUqDp+9vySdu3bhr5XBsIECBAgAABAgQIEOiOgAnA7vSFlhAgsECBpokOkxwL7BhVEyBAgAABAgQIECBAgMC6BXwEeN2ECiBAgAABAgQIECBAgAABAgQIECDQXQF3AHa3b7SMAAECBAgQIECAAAECBIYI+PTGEBSrCBAg0CBgArABxyYCBAgQIECAAAECaxFoa3KirXLWcgyeQ4AAAQIECCyPgAnA5elLR0KAAAECBAgQIECAAAECBHZqevMgeXzHtUFCYPUETACuXp87YgIEeiDQdNLmhK0HHaiJBAgQIECAAAECBAgQ6JCACcAOdYamECCwGgIm91ajnx0lgWkEvC5Mo2VfAgQIECBAgACBaQX8F+BpxexPgAABAgQIECBAgAABAgQIECBAoEcC7gDsUWdpKoE+CDTdxZLt9/HVPvSiNhIgQGC8QNPrvdf68X72IECAAAECBAjMU8AE4Dy11UWAAAECBAgQIECAAIEJBEyyT4BkFwIECBCYWMAE4MRUdiRAgAABAv0QaLpozCNwd1Y/+lEr2xdo+t3we9G+txIJEJheoOl1KkvzWjW9qWcQILBDwHcAGgkECBAgQIAAAQIECBAgQIAAAQIElljAHYBL3LkOjQABAgQIECBAgAABAgQIEFibQNMdme7GXJupZy1OwATg4uzVTGBlBZr+kCaKP6YrOzQcOAECBAgQ6LRA0zmM85dOd53GESBAYOUFTACu/BAAsAoCTSerefxOWFdhFDhGAgQIECBAgACBQYGm82TnyINaHhMg0GcBE4B97j1tJ0CAwBgBJ7VjgGwmQIAAAQIECBAgQIDACgiYAFyBTnaIBAgQIECAAAECBAgQ6IKANye70AvaQIDAKgqYAFzFXnfMBAgQIECAAAECBAgQIECgBYGmSd0sfhEfpW5q0yLa0wKzIgisW8AE4LoJFUCAAAECBAgQIEBgcQJNF7rZKhe7i+sbNRMgQIAAga4ImADsSk9oBwECvyDgYuYXODwgsPQCfueXvosdIIHOCzS9DplE7Xz3aeCMBJp+L7JKvxszglcsgRkImACcAaoiCRAgQIDAqgi4MFiVnnacBAgQIECAAAECfRbYpc+N13YCBAgQIECAAAECBAgQIECAAAECBJoFTAA2+9hKgAABAgQIECBAgAABAgQIECBAoNcCJgB73X0aT4AAAQIECBAgQIAAAQIECBAgQKBZwHcANvvYSoAAAQIECBAgQIBAywK+P7RlUMURWIOA38M1oHkKgR4LmADscedpOgECBAj0R6DpJNt/0OtPP2opgVUXaHotSxuvZ6s+Qto5/qZxZoy1Y6wUAgRWT8AE4Or1uSNep4ATknUCejoBAgQIECBAgAABAgQIECAwVwETgHPlVhkBAgQIECBAgAABAgS6JeAN7vX3B8P1G867BH02b3H1LVrAPwFZdA+onwABAgQIECBAgAABAgQIECBAgMAMBUwAzhBX0QQIECBAgAABAgQIECBAgAABAgQWLeAjwIvuAfUTILBmgabb9rNQXxK9ZlpPJECAAAECBAgQINCawKTn7U37ObdvrTsUtKICJgBXtOMd9vIItPVHsqmc1PIHd3nGjCMhQIAAAQLLJNB0DlPOXybZZ5lMHMtyCzSN5zzyMu6XW8HRESAwrYAJwGnF7E9ghQWcbKxw57d06E1jyMlqS8iKIUCAAAECBAgQIECAwICA7wAcAPGQAAECBAgQIECAAAECBAgQIECAwDIJuANwmXrTsRAgQIAAAQIECBAgQGAGAu7inwGqIgkQIDBHAROAc8RWFQECBAgQIECAAAECyy1gomy5+9fRLV7A79ji+0AL+ilgArCf/abVBAgQIEBgJQUmOemfZJ+VxHPQBAgQIECAAAECKytgAnBlu/4XDnz/ePT8iE0R+0XcEPFfER+MODniugiJAAECBAgQIEAgBEwyGwYECBAgQIBA3wRMAPatx9pv7xOiyPdG3KVW9B6RP6KK58QyJwa/EyERIECAAAECBAgQIECAAAECBAj0TMAEYM86rOXmHh7lfSBi94hrIl4bcUb1+PhY/knEQRGnRuSE4NUR0gQCk9wZMMk+E1RlFwJzETBe58KskiUV8PvTr46dZ391pa7soe1bNzXe2Vj2maQ3m45rmnImqcs+BAgQIECAwGQCJgAnc1rWvU6KA8vJv5sijov4QkRJn4nMtyNeH5GTgC+K2BIhESBAgAABAgQIECBAgAABAgQI9Ehglx61VVPbFTgqintkVeQpsaxP/pWa3hiZ86sHL4jl7csGSwIECBAgQIAAAQIECBAgQIAAgX4ImADsRz/NopW/Uyv0XbV8PfvzePCeasXdYvno+kZ5AgQIECBAgAABAgQIECBAgACB7guYAOx+H82qhY+oCr42luc2VPK52raH1/KyBAgQIECAAAECBAgQIECAAAECPRAwAdiDTppREw+pys3/7pvf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g8t8AT9wju1RFYFBgcExOe7x9sECao/zYz2nRDSV8Y7YnvtJBBYlsCUqLmN0Y0MjjOcGHJsWIpAXod+LKON3cPnz2PY3DS0zphtwbJq7wGOjxisiBsfx4ON8c3KvEa0zpkfAWN2qwLYobXBcNj0eVXlb4zWvHS9saFNee+bH5yUCBAgQWIDA/lHnmyJysi/f7cx38POjkPkOfX73mkRgkQJNJzDDtm2foLG/Hft8JOLSiBuqZT5+XIREYNECW6IBZWxvnKAxxvMESHaZm8Ddo6YtEV+NyIu8vBvwuxHvjDg8YpJkTE+iZJ95COR4zvPhMyIuj7gxIu9cyjH9gYgnRoz6nrPYdFsypm+jkJmBwLYos5w3TLIc14Q2xuueUUn+7uQ1ZV5b5jXmBRF5zbl/hESAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQ6KTA/wG1cZrlTvgPYgAAAABJRU5ErkJggg==" width="640">



```python

```
