package testTask;

import intefacee.taskInterface;

public class task1 implements taskInterface {

	int count = 0;

	public void runTask() {

		System.out.println("����ִ������" + (++count));
	}

}
