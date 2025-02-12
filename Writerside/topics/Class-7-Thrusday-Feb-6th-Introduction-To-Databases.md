# Class #7 | Thursday Feb 6th | Introduction To Databases

> This file contains information about databases, and an introduction to terminology, ideas, and software related 
> products that work to allow us to manage and manipulate data. In general, this chapter will give us information to 
> work with databases through Java, but all important details are replicable in other languages.


## Databases In Java
<p>In Java, there are various offerings for databases, both online, url only, and other formats. 
but in general here we will use the Apache Derby database. In general, what we will see is how 
to access a database through Java. 
</p>
<p>While the concepts seen here are applied in Java, the same facilities can be found within 
other languages and their standard libraries. This then allow us to replicate the components 
learnt here, like SQL, in other languages provided we know the blueprint for database 
connectivity</p>

### What is a database?
<p>A database can be understood as <b><code>a collection of tables, where data, 
memory, relationships and labels are stored</code></b>. Often however, a database can be either 
defined as a <i><b><code>software program or data, this leads to confusing in 
various environments</code></b></i>.</p>
<p>There is a distinction here between what we have within a database (data), and the software 
databases that can be found online (Oracle, ADABAS, AS400, MySQL, PosGres, SQLServer, etc.). The 
difference lies in whether we talk about software or data.
</p>
<p>often talking about databases, we often talk about <b><code>DBMS systems, 
or database management systems</code></b>. In general, however, when thinking about databases, 
we come to the notions of <b><code>tables and relationships</code></b></p>
<p>Therefore, from the <i>data point of view</i>, we generally talk about <code>tables 
and relationships</code>, from the <i>software point of view</i>, we talk about 
<b><code>database management systems</code></b></p>


### Glossary of Database Terms
<deflist>
<def title="Engine">
<p>The database engine is the core component that handles the SQL engine that allows us to 
modify the database internally. Effectively, <b>it is a component that allow us to manage these 
tables, i.e., the whole database</b>. When thinking of only this component, database access is 
done through console (this is the lowest form of a DBMS we can get)</p>
</def>
<def title="Tooling"><p>Over the database engine, we can get various components, for 
example a report manager, a screen manager, etc. These are tools that are added on top of 
the existent engine, it is not a GUI but it is a set of tools that abstract the process of 
creating reports, for example.
</p></def>
<def title="GUI"> <p>A database's GUI is a visible interface to access, manage, and modify our 
database, and the tooling that comes included along with the engine. Above this, 
applications are built, for example, an application generator, an SRP system, and ERP 
system, etc.</p></def>
</deflist>

<p>Often, enterprise grade database systems come with a limit set of tools, and some cases they 
do not come with tooling or a GUI. There are open source utilities, or in some cases, community 
edition versions of beloved database systems.
</p>

### ODBC 
<p>ODBC also known as <b><code>Open Database Communication*</code></b>, is a communication 
protocol invented by <i><b><code>Microsoft</code></b></i>, a driver, which connects directly to 
the engine, connecting the database engine and a high-level language.</p>
<p>The idea is to send SQL statements through a high-level language and get the results of said 
commands in parseable structures in the high-level language we defined, effectively abstracting 
communication with the SQL engine directly. The ODBC driver has grown in popularity, and in 
tooling, and once Java comes around, we grab it and build <i><b><code>JDBC</code></b></i>, or 
Java Database Connection, which is effectively a wrapper of the driver to make it work in Java.</p>
<p>The idea of the JDBC software is to <b><code>bring us closer to the GUI and the 
tooling that is expensive from enterprise systems, allowing us to manage databases 
without investing resources.</code></b></p>

### Apache Derby 
<p>Derby is a database engine built in java, it can be run embedded <b><code>which 
means it runs with the JVM on our local machine</code></b>, or as a 
<b><code>client-server</code></b> 
or <b><code>url only </code></b>
database. 
Nevertheless, the Java environment will never know what we are doing, we effectively only handle 
connections in our code. We do not need to have apache derby exactly, because the JDBC driver is 
mapped <b><code>to the database system</code></b>, this means that we need the exact driver to 
work with a database.
</p>
<p></p>