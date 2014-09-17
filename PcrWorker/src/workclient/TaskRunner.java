package workclient;

import intefacee.taskInterface;
import bean.Task;

public class TaskRunner {
	Task task = null;

	public TaskRunner(Task task) {
		this.task = task;
	}

	public void dotask() {
		try {
			Class cls = Class.forName(task.getClazz());
			final taskInterface ti = (taskInterface) cls.newInstance();

			new Thread(new Runnable() {
				boolean isrun = true;
				int count = 0;

				@Override
				public void run() {
					while (isrun) {
						ti.runTask();
						count++;
						if (count == 100)
							isrun = false;
					}
				}
			}).start();

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			System.out.println("任务文件没有找到");
			e.printStackTrace();
		}
	}
}
