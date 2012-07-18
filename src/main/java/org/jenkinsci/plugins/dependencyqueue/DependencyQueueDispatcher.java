package org.jenkinsci.plugins.dependencyqueue;


import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Hudson;
import hudson.model.Queue;
import hudson.model.queue.CauseOfBlockage;
import hudson.model.queue.QueueTaskDispatcher;

@Extension
public class DependencyQueueDispatcher extends QueueTaskDispatcher {

    @Override
    public CauseOfBlockage canRun(Queue.Item itemInQuestion) {
        for (Queue.Item queuedItem : Hudson.getInstance().getQueue().getItems()) {
            if (queuedItem.task instanceof AbstractProject && ((AbstractProject) queuedItem.task).getTransitiveDownstreamProjects().contains((AbstractProject) itemInQuestion.task)) {
                return CauseOfBlockage.fromMessage(Messages._DependencyQueueDispatcher_UpstreamInQueue(queuedItem.task.getName()));
            }
        }
        return super.canRun(itemInQuestion);
    }
}
