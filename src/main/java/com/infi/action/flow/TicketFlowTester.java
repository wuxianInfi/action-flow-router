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
package com.infi.action.flow;

import org.junit.Before;
import org.junit.Test;

import com.infi.action.flow.core.ActionExecutor;
import com.infi.action.flow.core.StepKey;
import com.infi.action.flow.ticket.TicketActionFlow;
import com.infi.action.flow.ticket.TicketActionFlowBuilder;
import com.infi.action.flow.ticket.TicketObject;
import com.infi.action.flow.ticket.TikcetActionExecutor;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月5日 下午6:23:21
 * @since JDK 1.8
 */
public class TicketFlowTester {

  ActionExecutor<TicketObject> executor;

  @Before
  public void init() {
    executor = new TikcetActionExecutor(new TicketActionFlowBuilder().build());
  }

  @Test
  public void testExecuteSingleStep() throws Exception {
    executor.executeSingleStep(new TicketObject(2),
        StepKey.of(TicketActionFlow.START, TicketActionFlow.CREATE));
  }

  @Test
  public void testRejectTicket() throws Exception {
    executor.executeAllSteps(new TicketObject(1),
        StepKey.of(TicketActionFlow.START, TicketActionFlow.CREATE));
  }

  @Test
  public void testAcceptTicket() throws Exception {
    executor.executeAllSteps(new TicketObject(2),
        StepKey.of(TicketActionFlow.START, TicketActionFlow.CREATE));
  }

}
