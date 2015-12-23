package org.jenkinsci.plugins.dependencyqueue;

import hudson.Extension;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.model.AbstractProject;

import org.kohsuke.stapler.DataBoundConstructor;

public class BlockWhileUpstreamQueuedProperty extends JobProperty<AbstractProject<?, ?>> {
    
    //default to true for backward compatibility
    private boolean blockWhileUpstreamQueued = true;
    
    @DataBoundConstructor
    public BlockWhileUpstreamQueuedProperty(Boolean blockWhileUpstreamQueued) {
        this.blockWhileUpstreamQueued = blockWhileUpstreamQueued;
    }
    
    public boolean isBlockWhileUpstreamQueued() {
        return blockWhileUpstreamQueued;
    }
    
    @Extension
    public static final class DescriptorImpl extends JobPropertyDescriptor {

        @Override
        public String getDisplayName() {
            return "Block if upstream jobs are queued.";
        }
    }
}
