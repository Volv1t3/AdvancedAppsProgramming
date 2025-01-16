# Class #2 | Thursday 16th | Streams—A Closer Look

> This file will contain information about Streams and their in depth definitions, methods, methodology, as well as 
> some examples in Java code.


## Stream Overview
<p>Streams are functional programming pipelines of information that <code>allow the programmer to define 
intermediate operations</code>, on the data in the stream. Additionally, we are capable of collecting, or 
collecting-reducing the data that is returned by a Stream into objects or data structures for further processing.
<br/><br/>
Streams offer various methods to both <code>parallelize, alter, generate, and operate </code> on data sources like 
<code>data structures, user defined structures, as well as files, zip files, or even JAR files</code>.
<br/><br/>
Based on the information provided on this course we will review some of the methods defined in te Stream Interface, 
as intermediate operations. <code>(filtering, processing, and modification operations that are done over a 
mutable Stream</code>. After these operations, a <code>terminal operation</code>, is defined as an operation that 
<code>produces a final output on the stream, either converting it to  collection, or reducing it to a certain 
output</code>
</p>

### Stream < T > Interface | Continuation


#### Stream Definition


#### Originator Sources | Generators, Buiders, etc.


#### Intermediate Operations


##### Short Circuiting Operations 


##### Stateful vs Not-Stateful Intermediate Operations


#### Terminal Operations | Collectors, Reductions

##### Optional Values As Return Types | Specifications, and Generics

