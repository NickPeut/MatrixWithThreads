# MatrixWithThreads
## Агрументы командной строки:
<Путь к файлу с первой матрицей> <Путь к файлу со второй матрицей> <количество потоков> <Путь к файлу для записи итоговой матрицы>
# Запуск умножения для первой пары матриц (700x800; 800x700)
## simple умножение (без использования ExecutorService) 
Время вычислений: 4753мс.
## 1 потока 
Время вычислений: 5284мс.
## 2 потока 
Время вычислений: 2448мс.
## 3 потока 
Время вычислений: 1882мс.
## 4 потока 
Время вычислений: 1579мс.
## 5 потока 
Время вычислений: 1882мс.
## 8 потока 
Время вычислений: 1857мс.
## 16 потока 
Время вычислений: 1868мс.
# Запуск умножения для второй пары матриц (600x800; 800x600)
## simple умножение (без использования ExecutorService) 
Время вычислений: 5672мс.
## ExecutorService:
### 1 поток 
Время вычислений: 16036мс.
### 2 потока 
Время вычислений: 10404мс.
### 3 потока 
Время вычислений: 9559мс.
### 4 потока 
Время вычислений: 9608мс.
### 8 потока 
Время вычислений: 10550мс.
# Выводы:
1. Разница во времени выполнения перемножения без использования потоков и с использованием ExecutorService объясняется накладными расходами использования потоков, что нивелируется при работе с большими данными.
2. Минимальное время работы достигается при использовании 3х потоков и приближено ко времени выполнения при использовании 4х потоков, так как программа запущена на устройстве с 4-х ядерным процессором.
3. При количестве потоков более 4х время выполнения возрастает, так как более 4х потоков не могут выполняться одновременно на данном устройстве.  
