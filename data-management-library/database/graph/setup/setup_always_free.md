# Setup a Graph environment with Always Free Tier#

## Introduction
Property graphs have become a useful way to model, manage, query and analyze much of the connected data found in today’s applications and information systems.  They allow you to represent data based on relationships and connectivity, query data by traversing those connections, and analyze data using algorithms that evaluate the strength of the connections, patterns and anomalies in the graph, the importance of elements in the graph, and other factors.

Oracle Database property graph support consists of graph storage, indexing, and search; a powerful in-memory analyst with 50 built-in, parallel analytic functions; a graph query language and developer APIs. Graph algorithms enable you to explore and discover relationships in social networks, IoT, big data, data warehouses and complex transaction data for applications such as fraud detection in banking, customer 360, and smart manufacturing.

Watch the video below for an overview of Oracle Graph.
[](youtube:-DYVgYJPbQA)

## Overview
This lab shows you how to deploy, configure, and use the Graph Server and Client package to query, analyze, and visualize graph content stored in a Free Tier Autonomous Database (ADB) instance. 

In this lab you will:
- Provision Free Tier Compute and ADB Shared instances.
- Deploy the Graph Server and Client package on the Linix compute instance you provisioned.
- Create and populate tables in the ADB instance.
- Query, analyze, and visualize that data as graphs.

## Notes specific to this workshop for Lab 3.3 (Setup ADB Free Tier)

### In STEP 1:Choosing ADW or ATP from the Services Menu.
- Choose Autonomous Transaction Processing (ATP)) from the menu the 3rd step.     
- Then Choose Transaction Processing as the workload type in step 5.

### In STEP 2: Creating the ADB instance
- Choose Transaction Processing as the workload type in step 5.
- Select the Always Free option in step 6: Configure the database. That is, provision a database with 1 OCPU and 20Gb of storage.


## Acknowledgements ##

- **Author** - Jayant Sharma - Product Manager, Spatial and Graph.  
  With a little help from colleagues (Albert Godfrind and Ryota Yamanaka).  
  Thanks to Jenny Tsai for helpful, constructive feedback that improved this workshop.
