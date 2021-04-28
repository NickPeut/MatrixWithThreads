# MatrixWithThreads
## Агрументы командной строки:
<Путь к файлу с первой матрицей> <Путь к файлу со второй матрицей> <количество потоков> <Путь к файлу для записи итоговой матрицы>
## Запуск умножения для первой пары матриц (700x800; 800x700)
### simple умножение (без использования ExecutorService) 
Время вычислений: 4753мс.
### ExecutorService:
* 1 поток 

Время вычислений: 5284мс.
* 2 потока 

Время вычислений: 2448мс.
* 3 потока 

Время вычислений: 1882мс.
* 4 потока 

Время вычислений: 1579мс.
* 5 потоков 

Время вычислений: 1882мс.
* 8 потоков 


Время вычислений: 1857мс.
* 16 потоков 
Время вычислений: 1868мс.
## Запуск умножения для второй пары матриц (1000x1000; 1000x1000)
### simple умножение (без использования ExecutorService) 
Время вычислений: 24800мс.
### ExecutorService:
* 1 поток
Время вычислений: 24589мс.
* 2 потока 
Время вычислений: 17460мс.
* 3 потока 
Время вычислений: 14092мс.
* 4 потока 
Время вычислений: 6784мс.
* 5 потоков 
Время вычислений: 10476мс.
* 8 потоков 
Время вычислений: 8761мс.
* 16 потоков 
Время вычислений: 7272мс.

## Выводы:
1. Разница во времени выполнения перемножения без использования потоков и с использованием ExecutorService и 1 потока объясняется накладными расходами использования ExecutorService, что нивелируется при работе с большими данными.
2. Минимальное время работы достигается при использовании 4х потоков, так как программа запущена на устройстве с 4-х ядерным процессором.
3. При количестве потоков более 4х время выполнения возрастает, так как более 4х потоков не могут выполняться одновременно на данном устройстве.  
