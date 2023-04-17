import {check} from 'k6';
import http from 'k6/http';

export const options = {
    stages: [
        { duration: '30s',  target: 10  },
        { duration: '30s',  target: 100 },
        { duration: '3m',   target: 100 },
        { duration: '1m',   target: 0}
    ],
};

export default function () {
    const res = http.get(`http://istio-ingressgateway-istio-system.apps.rosa-ozimakov.cns5.p1.openshiftapps.com/productpage`);
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}
