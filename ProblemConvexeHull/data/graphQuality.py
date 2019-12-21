import numpy as np
import matplotlib.pyplot as plt

with open("testQuality") as data:
    flag = 0
    for line in data:
        if(line[0] == "t" or line[0] == "d"):
            flag += 1
            continue
        if (flag == 1):
            x_str = line
            continue
        if (flag == 2):
            y_str = line
            continue

x_str = x_str.split(" ")
y_str = y_str.split(" ")
x = [int(x_str[i]) for i in range(len(x_str) - 1)]
y = [float(y_str[i]) for i in range(len(y_str) - 1)]

plt.ylim((0, 1.1))
plt.bar(x, y)

plt.xticks(fontsize=20)
plt.yticks(fontsize=20)
plt.title('Qualitiy-Rectangle', fontsize=20)
plt.xlabel('Test number', fontsize=20)
plt.ylabel('Qualities', fontsize=20)

plt.legend()
plt.show()
