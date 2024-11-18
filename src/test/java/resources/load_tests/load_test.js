import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    vus: 1000, // Увеличьте количество виртуальных пользователей
    duration: '60s', // Длительность теста по-прежнему 30 секунд
};

export default function () {
    const url = 'http://host.docker.internal:8080/api/v1/wallet';

    const walletId = "43fe0cf8-c985-4db7-b578-92f5c338d200";
    const operationType = "DEPOSIT";
    const amount = "1";

    const payload = JSON.stringify({
        walletId: walletId,
        operationType: operationType,
        amount: amount,
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Выполняем запрос
    let res = http.post(url, payload, params);

    // Проверка ответа
    check(res, {
        'is status 200': (r) => r.status === 200,
        'response time is acceptable': (r) => r.timings.duration < 500,
    });

    // Ожидание между запросами: отк комментируйте в зависимости от нужной нагрузки
    // Если хотите тестировать с высокой нагрузкой, удалите или уменьшите задержку
    // sleep(0.1); // Ожидание 100ms между запросами для повышения rps
}