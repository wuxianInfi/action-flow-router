/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.infi.action.flow.ticket;

import java.util.Map;

import com.google.common.collect.Maps;
import com.infi.action.flow.core.Action;
import com.infi.action.flow.core.ActionFlowBuilder;
import com.infi.action.flow.core.ActionStep;
import com.infi.action.flow.core.StepKey;
import com.infi.action.flow.ticket.action.ApproveTicketAction;
import com.infi.action.flow.ticket.action.CreateTicketAction;
import com.infi.action.flow.ticket.action.FinishTicketAction;
import com.infi.action.flow.ticket.action.ReUpdateTicketAction;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月5日 下午5:03:23
 * @since JDK 1.8
 */
public class TicketActionFlowBuilder implements ActionFlowBuilder<TicketObject> {

  private Action<TicketObject> createAction = new CreateTicketAction();
  private Action<TicketObject> approveAction = new ApproveTicketAction();
  private Action<TicketObject> reupdateAction = new ReUpdateTicketAction();
  private Action<TicketObject> finishAction = new FinishTicketAction();
  private Map<StepKey, ActionStep<TicketObject>> steps = Maps.newHashMap();

  public TicketActionFlowBuilder build() {
    steps.put(StepKey.of(TicketActionFlow.START, TicketActionFlow.CREATE),
        new ActionStep<TicketObject>(createAction, r -> StepKey.of(TicketActionFlow.CREATE, TicketActionFlow.APPROVE)));
    steps.put(StepKey.of(TicketActionFlow.CREATE, TicketActionFlow.APPROVE),
        new ActionStep<TicketObject>(approveAction, r -> {
          return r.isSuccess() ? StepKey.of(TicketActionFlow.APPROVE, TicketActionFlow.CLOSE)
              : StepKey.of(TicketActionFlow.APPROVE, TicketActionFlow.REUPDATE);
        }));
    steps.put(StepKey.of(TicketActionFlow.APPROVE, TicketActionFlow.REUPDATE),
        new ActionStep<TicketObject>(reupdateAction, r -> {
          return StepKey.of(TicketActionFlow.REUPDATE, TicketActionFlow.APPROVE);
        }));
    steps.put(StepKey.of(TicketActionFlow.REUPDATE, TicketActionFlow.APPROVE),
        new ActionStep<TicketObject>(approveAction, r -> {
          return r.isSuccess() ? StepKey.of(TicketActionFlow.APPROVE, TicketActionFlow.CLOSE)
              : StepKey.of(TicketActionFlow.APPROVE, TicketActionFlow.REUPDATE);
        }));
    steps.put(StepKey.of(TicketActionFlow.APPROVE, TicketActionFlow.CLOSE), new ActionStep<TicketObject>(finishAction, r -> {
      return StepKey.of(TicketActionFlow.CLOSE, TicketActionFlow.END);
    }));
    return this;
  }

  @Override
  public Map<StepKey, ActionStep<TicketObject>> steps() {
    return this.steps;
  }
}
