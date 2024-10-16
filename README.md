Here's an updated `README.md` section with the instructions:

---

## How to Launch the Project

### Step 1: Navigate to the Project Root Directory
- Make sure you're in the root directory of the project where the `pom.xml` file is located.

### Step 2: Compile the Project

- To compile the project, use Maven with the following command:
    ```
    mvn clean package
    ```

### Step 3: Launch the Project with Command-Line Arguments

- **With a .txt file path**: You can specify the path to a `.txt` file in the command-line arguments. Replace `C:\Users\user\Desktop\java\lng.txt` with your actual file path:
  ```
  java -jar target/disjoint-groups-1.0-SNAPSHOT.jar C:\Users\user\Desktop\java\lng.txt
  ```

- **Without command-line arguments**: If no file path is provided, the `.gz` archive will be automatically downloaded (and then processed)from the following link:
  ```
  https://github.com/PeacockTeam/new-job/releases/download/v1.0/lng-4.txt.gz
  ```
 -  Example command to run without arguments:
  ```
  java -jar target/disjoint-groups-1.0-SNAPSHOT.jar
  ```

### Step 4: Launch with Memory Limit
- You can limit the memory usage of the project to 1GB by using the `-Xmx` option. For example:
    ```
    java -Xmx1G -jar target/disjoint-groups-1.0-SNAPSHOT.jar C:\Users\user\Desktop\java\lng.txt
    ```
- or without arguments:
    ```
    java -Xmx1G -jar target/disjoint-groups-1.0-SNAPSHOT.jar
    ```

---



## Задание
С помощью Java:

1. Считать файл (https://github.com/PeacockTeam/new-job/releases/download/v1.0/lng-4.txt.gz), состоящий из строк вида 

```
A1;B1;C1
A2;B2;C2
A3;B3
...
```
> [!NOTE]
> в строке может быть неограниченное число элементов

2. Найти множество уникальных строчек и разбить его на непересекающиеся группы по следующему критерию:
> Если две строчки имеют совпадения непустых значений в одной или более колонках, они принадлежат одной группе. 

Например, строчки
```
111;123;222
200;123;100
300;;100
```

все принадлежат одной группе, так как первые две строчки имеют одинаковое значение 123 во второй колонке, а две последние одинаковое значение 100 в третьей колонке

строки

```
100;200;300
200;300;100
```

не должны быть в одной группе, так как значение 200 находится в разных колонках

3. Вывести полученные группы в файл в следующем формате:

```
Группа 1
строчка1
строчка2
строчка3
...

Группа 2 
строчка1
строчка2
строчка3
```

- В начале вывода указать получившиееся число групп с более чем одним элементом.
- Сверху расположить группы с наибольшим числом элементов.

4. После выполнения задания необходимо отправить количество полученных групп с более чем одним элементом и время выполнения программы (мы не проверяем код если ответ неверный).
5. Код необходимо выложить на github или gitlab.

## Требования
1. Допустимое время работы - до 30 секунд.
2. Проект должен собираться с помощью maven или gradle в исполняемый jar.
3. jar должен запускаться следующим образом: `java -jar {название проекта}.jar тестовый-файл.txt`
4. Алгоритм не должен потреблять > 1Гб памяти (запускать с ограничением по памяти `-Xmx1G`)

## Примечание
1. Строки вида
```
 "8383"200000741652251"
 "79855053897"83100000580443402";"200000133000191"
```
являются некорректными и должны пропускаться

2. Если в группе две одинаковых строки - нужно оставить одну

