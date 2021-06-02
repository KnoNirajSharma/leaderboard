# Knoldus LeaderBoard
- [Knoldus LeaderBoard](#knoldus-leaderboard)
  - [Introduction](#introduction)
  - [Resources](#resources)
  - [Setup and Access](#setup-and-access)
      - [1 - Install the Google Cloud SDK](#1---install-the-google-cloud-sdk)
      - [2 - Install the Kubectl Component](#2---install-the-kubectl-component)
      - [3 - Retrieve Cluster Credentials](#3---retrieve-cluster-credentials)
      - [4 - Confirm and Tune the Context](#4---confirm-and-tune-the-context)
  - [Permissions](#permissions)

## Introduction
This article provides the reader with information on how to set up their local system to access the leaderboard application. As all compute infrastructure and applications run in a Google Kubernetes Engine (GKE) cluster, access is powered by these command-line interfaces:

* [glcoud](https://cloud.google.com/sdk/gcloud)
* [kubectl](https://kubernetes.io/docs/reference/kubectl/overview/)

These utilities are included in the **[Google Cloud SDK](https://cloud.google.com/sdk#section-1)**, and once installed and configured, provide the user with ongoing access to the environment from a terminal window. Additionally, **kubectl** can forward one or more local ports to a pod to provide access to an application's web interface.

<details>
<summary>:mag_right:</summary>  
> <sub>GKE provides a managed environment for containerized applications using Google infrastructure powered by Kubernetes. The environment consists of nodes (Compute Engine instances) grouped together to form a cluster. Nodes have been further organized by role into pools.</sub>
</details>

## Tech Stacks
<img alt="Scala" src="https://img.shields.io/badge/scala-%23FA7343.svg?&style=for-the-badge&logo=scala&logoColor=white"/> <img alt="TypeScript" src="https://img.shields.io/badge/typescript%20-%23007ACC.svg?&style=for-the-badge&logo=typescript&logoColor=white"/> <img alt="HTML5" src="https://img.shields.io/badge/html5%20-%23E34F26.svg?&style=for-the-badge&logo=html5&logoColor=white"/>  <img alt="SCSS" src="https://img.shields.io/badge/-scss-E10098?style=for-the-badge&logo=scss"/> <img alt="Kotlin" src="https://img.shields.io/badge/kotlin-%230095D5.svg?&style=for-the-badge&logo=kotlin&logoColor=white"/>  <img alt="JavaScript" src="https://img.shields.io/badge/javascript%20-%23323330.svg?&style=for-the-badge&logo=javascript&logoColor=%23F7DF1E"/> 


## Resources
The following leaderboard resources have been provisioned within the environment.  

| Name | Email |  
| :--- | :--- |  
| Himanshu Gupta | himanshu.gupta@knoldus.com |  
| Shubham Girdhar | shubham.girdhar@knoldus.com |

## Setup and Access
#### 1 - Install the Google Cloud SDK

```shell
curl https://sdk.cloud.google.com | bash
exec -l $SHELL
gcloud init
```
<details>
<summary><sub>OUTPUT</sub></summary>
<sub>
```
Welcome! This command will take you through the configuration of gcloud.
Your current configuration has been set to: [default]
You can skip diagnostics next time by using the following flag:
  gcloud init --skip-diagnostics
Network diagnostic detects and fixes local network connection issues.
Checking network connection...done.
Reachability Check passed.
Network diagnostic passed (1/1 checks passed).
You must log in to continue. Would you like to log in (Y/n)?  
```
</sub>
</details>
After auto-switching to your browser, select the Knoldus email account you've already provided, authenticate, and then allow access. The following message is then displayed:

`You are now authenticated with the Google Cloud SDK!`
Back in your terminal, the following information is displayed:

```Your browser has been opened to visit: https://accounts.google.com/o/oauth2/auth? ... 
You are logged in as: `[user@knoldus.com]`.

Pick cloud project to use:  
 [1] sonarqube-289802
 [2] (random-generated project name)   
 [3] Create a new project  

Please enter numeric choice or text value (must exactly match list item):
```
Select the numeric choice for the `sonarqube-289802` project.

#### 2 - Install the Kubectl Component
```shell
gcloud components install kubectl
```

#### 3 - Retrieve Cluster Credentials
```shell
gcloud container clusters get-credentials allknols --zone us-central1-c --project sonarqube-289802
```
<details>
<summary><sub>OUTPUT</sub></summary>
<sub>

```
Fetching cluster endpoint and auth data.
kubeconfig entry generated for allknols.
```

</sub>
</details>  

#### 4 - Confirm and Tune the Context
```shell
kubectl config get-contexts
kubectl config set-context $(kubectl config current-context) --namespace=leaderboard
```
<details>
<summary><sub>OUTPUT</sub></summary>
<sub>

```
CURRENT   NAME                                 CLUSTER                              AUTHINFO                             NAMESPACE
*         allknols                                      gke_sonarqube-289802_us-central1-c_allknols   gke_sonarqube-289802_us-central1-c_allknols   

Context "gke_sonarqube-289802_us-central1-c_allknols" modified.

```

</sub>
</details>


## Permissions

Once the steps from the [Setup and Access](#setup-and-access) section are complete, you are ready to work in the environment. Access to  cloud resources is based on the [Principle of Least Privilege](https://en.wikipedia.org/wiki/Principle_of_least_privilege) (PoLP). Your GCP IAM user account has the following roles:

```
compute.projects.get
container.clusters.get
container.clusters.list
container.pods.get
container.pods.getLogs
container.pods.list
resourcemanager.projects.get
```
