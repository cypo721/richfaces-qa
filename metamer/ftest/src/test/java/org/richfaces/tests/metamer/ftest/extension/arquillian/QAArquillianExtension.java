/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2016, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.tests.metamer.ftest.extension.arquillian;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.as.cli.CommandLineException;

/**
 */
public class QAArquillianExtension implements LoadableExtension {

    @Override
    public void register(ExtensionBuilder builder) {
        applyPatch();
        builder.observer(Executor.class);
    }

    public static void applyPatch() {
        String pathToPatch = System.getProperty("pathToPatch");
        if (pathToPatch != null && !pathToPatch.isEmpty()) {
            try {
                applyPatch(pathToPatch);
            } catch (Throwable t) {
                System.err.println(t);
                System.err.println("Was not able to apply the patch. Exiting.");
                System.exit(1);
            }
        }
    }

    /**
     * Apply patch from given path.
     */
    private static void applyPatch(String pathToPatch) throws CommandLineException {
        System.out.println("### Applying patch from path <" + pathToPatch + ">");

        String pathToJBossHome = System.getProperty("JBOSS_HOME");
        try {
            File f = new File(pathToJBossHome + "/bin/jboss-cli.sh");
            f.setExecutable(true);

            String cmd = f.getAbsolutePath() + " --command=\"patch apply " + pathToPatch + "\"";
            String[] cmdArray = new String[] { "/bin/sh", "-c", cmd };

            // apply patch by running jboss-cli.sh with parameters
            Process p = Runtime.getRuntime().exec(cmdArray);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            BufferedReader stdeRR = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // check stdin contains success
            String s;
            boolean success = false;
            while ((s = stdInput.readLine()) != null) {
                if (s.toLowerCase().contains("success")) {
                    success = true;
//                    break;
                }
            }
            p.destroy();
            // check stdin contained success, otherwise immediatelly exit
            if (!success) {
                System.err.println("Attempt to apply a patch was not succesfull. Exiting.");
                System.exit(1);
            }
        } catch (IOException ex) {
            System.err.println(ex);
            System.err.println("Attempt to apply a patch was not succesfull. Exiting.");
            System.exit(1);
        }
        System.out.println("### patch applied succesfully");
    }
}
