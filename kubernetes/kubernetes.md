# Content
  1. Introduction
  2. Container Overview
  3. Container Orchestration
  4. Introdcution to kubernetes
  5. Kubernetes Features and benefits
  6. Kubernetes Architecture
  7. Kubernetes Setup
  8. Demo - Minikube-Setup
  9. Kubernetes Components
  10. PODS
  11. Demo - PODS
  12. PODS and YAML
  13. Demo - PODs with YAML
  14. Replication Controllers and ReplicaSets
  15. Demo - ReplicaSets
  16. Deployments
  17. Demo - Deployments
  18. Deployments - updates and Rollback 
  19. Demo - Updates and Rollback 
  20. Networking in kubernetes
  21. Services
  22. Demo - Services
  23. Microservices Architectire
  24. Demo - Deploying app on pods
  25. Demo - Deploying app on with Deployments
  26. kubernetes on GCP(GKE)


# Introduction

  - Introduction to kubernetes

# Introduction to kubernetes

  - Kubernetes is an open source container orchestration platform that automates the deployment scaling 
    and management of containerized applications.
  
  - It's an open source container orchestration platform, which was originally developed by Google and 
    is now maintained by Cloud Native Computing Foundation, CNCF.
  
  - Containers are lightweight way to package software, making it easier to deploy and run applications
    consistently across multiple environments.
  
  >  However, managing these containers at scale can be challenging.

**This is where Kubernetes comes in** 

  - It provides a platform to manage containerized applications. Abstracting away the underlying 
    infrastructure and making it easier to manage and scale applications.

  - It comes with a `master slave` model where the `master node` controls the cluster and is also responsible
    for making decisions about where to deploy applications.

  - The `worker nodes` run the containers and execute the workloads.

  - Kubernetes also provides a declarative approach to managing infrastructure, where you specify the desired
    state of the application, and Kubernetes takes care of making it happen. And when we look at the `master node` 
    components, it includes API server Etcd scheduler and control manager. the `worker nodes` will contain 
    container runtime such as Docker or Portman. And the Kubernetes agent, known as `Kubelet` and `Kubelet` is 
    responsible for communication between `master nodes` and `worker nodes`.

  > So in summary, Kubernetes is an essential tool for managing containerized application at scale, and 
    it is also used by a lot of organizations around the world. So whether you are on a small project or 
    a large application, Kubernetes helps you manage your infrastructure more efficiently and effectively.

