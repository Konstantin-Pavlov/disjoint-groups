

---

## How to Launch the Project

### Step 1: Navigate to the Project Root Directory
- Make sure you're in the root directory of the project where the `pom.xml` file is located.

### Step 2: Compile the Project

- To compile the project, use Maven with the following command:
    ```
    mvn clean package
    ```
<details>
  <summary>Screenshots of running this command</summary>
  
  <br> <!-- This line break helps separate the summary from the images -->

  ![Screenshot of the command](https://github.com/user-attachments/assets/5ea049d5-5581-4712-b01d-450dae942a49)

</details>


### Step 3: Launch the Project with Command-Line Arguments

- **With a .txt file path**: You can specify the path to a `.txt` file in the command-line arguments. Replace `C:\Users\user\Desktop\java\lng.txt` with your actual file path:
  ```
  java -jar target/disjoint-groups-1.0-SNAPSHOT.jar C:\Users\user\Desktop\java\lng.txt
  ```
  - **With a .csv file path**: You can specify the path to a `.csv` file in the command-line arguments. Replace `C:\Users\user\Desktop\java\lng-big.csv` with your actual file path:
  ```
  java -jar target/disjoint-groups-1.0-SNAPSHOT.jar C:\Users\user\Desktop\java\lng-big.csv
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

<details>
  <summary>Screenshots of running the program with different arguments</summary>

  <br> <!-- This line break helps separate the summary from the images -->

  ![Screenshot 1](https://github.com/user-attachments/assets/eb956860-bb22-4991-ad16-1cd717d7e71a)
  ![Screenshot 2](https://github.com/user-attachments/assets/6d6f530a-b928-41e3-a628-d37a7a01094f)

</details>




---



<details>
  <summary><strong style="font-size: 24px;">Задание (Russian)</strong></summary>

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

далее попробовать с большим .csv файлом: https://github.com/PeacockTeam/new-job/releases/download/v1.0/lng-big.7z

</details>


<details>
  <summary><strong style="font-size: 24px;">Task (English)</strong></summary>

Using Java:

1. Read a file (https://github.com/PeacockTeam/new-job/releases/download/v1.0/lng-4.txt.gz) consisting of lines in the following format:

```
A1;B1;C1
A2;B2;C2
A3;B3
...
```
> [!NOTE]
> Each line may contain an unlimited number of elements.

2. Find a set of unique lines and divide it into non-overlapping groups based on the following criterion:
> If two lines have non-empty matching values in one or more columns, they belong to the same group.

For example, the lines:
```
111;123;222
200;123;100
300;;100
```
all belong to the same group, as the first two lines have the same value of 123 in the second column, and the last two have the same value of 100 in the third column.

The lines:
```
100;200;300
200;300;100
```
should not be in the same group, as the value 200 is in different columns.

3. Output the resulting groups to a file in the following format:

```
Group 1
line1
line2
line3
...

Group 2 
line1
line2
line3
```

- At the beginning of the output, indicate the number of groups with more than one element.
- Groups with the largest number of elements should be listed first.

4. After completing the task, you must submit the number of groups with more than one element and the execution time of the program (we do not check the code if the answer is incorrect).
5. The code must be uploaded to GitHub or GitLab.

## Requirements
1. Acceptable execution time - up to 30 seconds.
2. The project should be built using Maven or Gradle into an executable JAR.
3. The JAR should be run as follows: `java -jar {project-name}.jar test-file.txt`
4. The algorithm must not consume more than 1GB of memory (run with memory limit `-Xmx1G`).

## Note
1. Lines like:
  ```
   "8383"200000741652251"
   "79855053897"83100000580443402";"200000133000191"
  ```
  are invalid and should be skipped.

2. If there are duplicate lines in a group, only one should be retained.

then try with a large .csv file: https://github.com/PeacockTeam/new-job/releases/download/v1.0/lng-big.7z

</details>
