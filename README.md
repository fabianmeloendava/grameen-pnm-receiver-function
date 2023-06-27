---
page_type: job
languages:
- java
  products:
- azure
  description: "This function pull the PayNearMe payments each day  to Azure Queue Storage.
---

# Receiver PNM Function

This is the function to pull the repayments information from  PNM to set into the Azure Storage Queue 

## Features

StorageService
MambuService

## Getting Started

### Prerequisites
#### Emulator for local Azure Storage development
With npm installed
npm install -g azurite
without npm installed:
git clone https://github.com/Azure/Azurite.git
npm install
npm run build
npm install -g
##### Run Azurite
azurite --silent --location c:\azurite --debug c:\azurite\debug.log

This project uses the Maven Wrapper, so all you need is Java installed.

### Installation

- Clone the project: `git clone https://github.com/grameen-america-inc/grameen-pnm-receiver-function.git`
- Configure the project to use your own resource group and your own application name (it should be unique across Azure)
  - Open the `pom.xml` file
  - Customize the `functionResourceGroup` and `functionAppName` properties
- Build the project: `./mvnw clean package`

### Quickstart

Once the application is built, you can run it locally using the Azure Function Maven plug-in:

`./mvnw azure-functions:run`


## Deploying to Azure Functions

Deploy the application on Azure Functions with the Azure Function Maven plug-in:

`./mvnw azure-functions:deploy`

You can then test the running application, by running a POST request:
