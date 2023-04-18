# Service Mesh Demo

## Prepare a namespace
```
oc new-project servicemesh-demo
oc label namespace servicemesh-demo argocd.argoproj.io/managed-by=openshift-gitops
oc adm policy add-role-to-user admin system:serviceaccount:openshift-gitops:openshift-gitops-argocd-application-controller -n servicemesh-demo
```

## Deploy with GitOps
Create an app:
 - `GIT URL:
   https://github.com/ozimakov-rh/servicemesh-demo.git`
 - `PATH: deploy`