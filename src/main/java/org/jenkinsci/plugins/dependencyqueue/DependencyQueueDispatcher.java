package org.jenkinsci.plugins.dependencyqueue;


import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Hudson;
import hudson.model.Queue;
import hudson.model.queue.QueueTaskDispatcher;
import hudson.model.queue.CauseOfBlockage;

@Extension
public class DependencyQueueDispatcher extends QueueTaskDispatcher {

    @Override
    public CauseOfBlockage canRun(Queue.Item itemInQuestion) {
        if (itemInQuestion.task instanceof AbstractProject) {
            AbstractProject<?,?> projectInQuestion = (AbstractProject) itemInQuestion.task;
            BlockWhileUpstreamQueuedProperty property = projectInQuestion.getProperty(BlockWhileUpstreamQueuedProperty.class);
            //assume old jobs won't have this property
            if(property != null && ! property.isBlockWhileUpstreamQueued()) {
            	//property is present and set to false.  Don't block.
                return null;
            }
            for (Queue.Item queuedItem : Hudson.getInstance().getQueue().getItems()) {
                if (queuedItem.task instanceof AbstractProject && ((AbstractProject) queuedItem.task).getTransitiveDownstreamProjects().contains(projectInQuestion)) {
                    return CauseOfBlockage.fromMessage(Messages._DependencyQueueDispatcher_UpstreamInQueue(queuedItem.task.getName()));
                }
            }
        }
        return super.canRun(itemInQuestion);
    }
}
