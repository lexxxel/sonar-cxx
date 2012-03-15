/*
 * Sonar Cxx Plugin, open source software quality management tool.
 * Copyright (C) 2010 - 2011, Neticoa SAS France - Tous droits reserves.
 * Author(s) : Franck Bonin, Neticoa SAS France.
 *
 * Sonar Cxx Plugin is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar Cxx Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar Cxx Plugin; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.cxx.valgrind;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyObject;

import java.net.URISyntaxException;

import org.junit.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.rules.Violation;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.plugins.cxx.TestUtils;

@Ignore // This unit test (the production code, actually) depends
        // on absolute paths in the report file and will fail if checked out
        // elsewhere. The valgrind sensor would *also* fail if the project
        // is moved to a different place after creating the reports and before
        // analyzing. Skip for now...
public class CxxValgrindSensorTest {
  private CxxValgrindSensor sensor;
  private SensorContext context;
  private Project project;
  
  @Before
  public void setUp() throws java.net.URISyntaxException {
    project = TestUtils.mockProject();
    RuleFinder ruleFinder = TestUtils.mockRuleFinder();
    sensor = new CxxValgrindSensor(ruleFinder, project);
    context = mock(SensorContext.class);
    Resource resourceMock = mock(Resource.class);
    when(context.getResource((Resource)anyObject())).thenReturn(resourceMock);
  }

  @Test
  public void shouldReportCorrectViolations() {
    sensor.analyse(project, context);
    verify(context, times(12)).saveViolation(any(Violation.class));
  }
}