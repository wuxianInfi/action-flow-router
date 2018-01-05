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

import com.infi.action.flow.core.ActionFlow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hongtao
 * @version v 0.1 , 2018年1月5日 下午4:52:42
 * @since JDK 1.8
 */
@RequiredArgsConstructor
public enum TicketActionFlow implements ActionFlow{

  START("开始"), CREATE("新建"), APPROVE("审核"), REUPDATE("重新修改"), CLOSE("关闭"), END("结束");

  @Getter
  private final String desc;

}
