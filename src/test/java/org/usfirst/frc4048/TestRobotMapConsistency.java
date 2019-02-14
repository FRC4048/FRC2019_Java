package org.usfirst.frc4048;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A test that verified that the Production RobotMap and the Test Chassis have the same constants.
 * The idea is that if someone added a new value to one of the RobotMaps the build will fail until it is added to the other onee.
 */
public class TestRobotMapConsistency {

    @Test
    public void testRobotMap() throws Exception {

        Set<String> productionRobotMap = getSymbols(RobotMapFor2018Robot.class);
        Set<String> testChassisRobotMap = getSymbols(RobotMapForTestbed.class);

        Set<String> onlyInProductionRobotMap = new HashSet<>(productionRobotMap);
        onlyInProductionRobotMap.removeAll(testChassisRobotMap);

        Set<String> onlyInTestChassisRobotMap = new HashSet<>(testChassisRobotMap);
        onlyInTestChassisRobotMap.removeAll(productionRobotMap);

        if (( ! onlyInProductionRobotMap.isEmpty()) || ( ! onlyInTestChassisRobotMap.isEmpty())) {
            System.err.println("Mismatch between RobotMapFor2018Robot and RobotMapForTestbed");
            if ( ! onlyInProductionRobotMap.isEmpty()) {
                System.err.println("Only in RobotMapFor2018Robot: " + onlyInProductionRobotMap.toString());
            }
            if ( ! onlyInTestChassisRobotMap.isEmpty()) {
                System.err.println("Only in RobotMapForTestbed: " + onlyInTestChassisRobotMap.toString());
            }

            Assert.fail();
        }

    }

    /**
     * Private utility to look up and return a list of hte public static constants in the given class
     *
     * @param klazz the class to inspect
     * @return Set of Strings representing the constants defined in the given class
     */
    private Set<String> getSymbols(Class klazz) {

        Field[] fields = klazz.getDeclaredFields();
        Set<String> result = Arrays.stream(fields)
                .map(this::getStaticFinalFieldName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return result;
    }

    /**
     * private utility to return a field name if the field is public static final
     *
     * @param field the field to inspect
     * @return the field name if it is a public static final field, null otherwise
     */
    private String getStaticFinalFieldName(Field field) {
        int modifiers = field.getModifiers();
        if ((Modifier.isPublic(modifiers)) && (Modifier.isStatic(modifiers)) && Modifier.isFinal(modifiers)) {
            return field.getName() + ":" + field.getType().toString();
        } else {
            return null;
        }
    }
}
