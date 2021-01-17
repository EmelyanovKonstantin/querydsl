package com.querydsl.collections;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.map;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.querydsl.core.group.GroupBy;
import org.junit.Test;

import com.querydsl.core.annotations.QueryEntity;


public class GroupBy4Test {

    @QueryEntity
    public static class Table {
        String col1, col2, col3;

        public Table(String c1, String c2, String c3) {
            col1 = c1;
            col2 = c2;
            col3 = c3;
        }
    }

    @Test
    public void test() {
        List<Table> data = new ArrayList<>();
        data.add(new Table("1", "abc", "111"));
        data.add(new Table("1", "pqr", "222"));
        data.add(new Table("2", "abc", "333"));
        data.add(new Table("2", "pqr", "444"));
        data.add(new Table("3", "abc", "555"));
        data.add(new Table("3", "pqr", "666"));

        QGroupBy4Test_Table table = QGroupBy4Test_Table.table;
        Map<String, Map<String, String>> grouped = CollQueryFactory
                .from(table, data)
                .transform(groupBy(table.col1).as(map(table.col2, table.col3)));

        assertEquals(3, grouped.size());
        assertEquals(2, grouped.get("1").size());
        assertEquals(new HashSet<>(Arrays.asList("abc", "pqr")),  grouped.get("1").keySet());

    }


    @Test
    public void test2() {
        List<Table> data = new ArrayList<>();
        data.add(new Table("1", "abc", "111"));
        data.add(new Table("1", "pqr", "222"));
        data.add(new Table("2", "abc", "333"));
        data.add(new Table("2", "pqr", "444"));
        data.add(new Table("3", "abc", "555"));
        data.add(new Table("3", "pqr", "666"));

        QGroupBy4Test_Table table = QGroupBy4Test_Table.table;
        final Map<String, Map<String, String>> transform = CollQueryFactory
                .from(table, data)
                .transform(groupBy(table.col2).as(map(GroupBy.max(table.col1), table.col2)));

        assertEquals(3, transform.size());
        assertEquals(2, transform.get("1").size());
        assertEquals(new HashSet<>(Arrays.asList("abc", "pqr")),  transform.get("1").keySet());

    }

}
