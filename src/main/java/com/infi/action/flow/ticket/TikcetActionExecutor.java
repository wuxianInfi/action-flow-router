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

import com.google.common.base.Preconditions;
import com.infi.action.flow.core.ActionExecutor;
import com.infi.action.flow.core.ActionFlowBuilder;
import com.infi.action.flow.core.ActionStep;
import com.infi.action.flow.core.StepKey;
import com.infi.action.flow.model.ExecutionResult;

import lombok.AllArgsConstructor;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月5日 下午6:05:07
 * @since JDK 1.8
 */
@AllArgsConstructor
public class TikcetActionExecutor implements ActionExecutor<TicketObject> {

  private final ActionFlowBuilder<TicketObject> builder;

  @Override
  public void executeSingleStep(TicketObject executionObject, StepKey startStepKey)
      throws Exception {
    ActionStep<TicketObject> step = builder.steps().get(startStepKey);
    Preconditions.checkNotNull(step).getAction().execute(executionObject);
  }

  @Override
  public void executeAllSteps(TicketObject executionObject, StepKey startStepKey) throws Exception {
    ActionStep<TicketObject> step;
    StepKey nextKey = startStepKey;
    while (nextKey != null && !nextKey.getTo().equals(TicketActionFlow.END)) {
      step = Preconditions.checkNotNull(builder.steps().get(nextKey));
      try {
        ExecutionResult executionResult = step.getAction().execute(executionObject);
        nextKey = step.getRouter().route(executionResult);
      } catch (Exception e) {
        e.printStackTrace();
        nextKey = step.getRouter().exceptionCaught(null, e);
      }
    }
  }

}
