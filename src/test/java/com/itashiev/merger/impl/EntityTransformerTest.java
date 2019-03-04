package com.itashiev.merger.impl;

import com.itashiev.merger.models.Lead;
import com.itashiev.merger.models.Pooling;
import com.itashiev.merger.models.PoolingTract;
import com.itashiev.merger.models.Quartering;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class EntityTransformerTest {

    private EntityTransformer entityTransformer;

    @Before
    public void setUp() {
        entityTransformer = new EntityTransformer();
    }

    @Test
    public void transform_EmptyList_ReturnsEmptyList() {
        //arrange
        List<Object> objects = Collections.emptyList();

        //act
        List actual = (List) entityTransformer.transform(objects);

        //assert
        Assertions.assertThat(actual).isEqualTo(Collections.emptyList());
    }

    @Test
    public void transform_UniqueList_ReturnsSameList() {
        //arrange
        List<Lead> leads = Arrays.asList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 2")
                        .build(),
                Lead.builder()
                        .id(2L)
                        .name("Lead 2")
                        .build()
        );

        //act
        List actual = (List) entityTransformer.transform(leads);

        //assert
        List<Lead> expected = Arrays.asList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 2")
                        .build(),
                Lead.builder()
                        .id(2L)
                        .name("Lead 2")
                        .build());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void transform_DuplicateElementsList_ReturnsUniqueList() {
        //arrange
        List<Lead> leads = Arrays.asList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .build(),
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .build()
        );

        //act
        List actual = (List) entityTransformer.transform(leads);

        //assert
        List<Lead> expected = Collections.singletonList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .build());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void transform_DuplicateNestedElementsList_ReturnsUniqueList() {
        //arrange
        List<Pooling> poolingsFirst = Collections.singletonList(
                Pooling.builder()
                        .id(11L)
                        .name("Pooling 11")
                        .build()
        );

        List<Pooling> poolingsSecond = Collections.singletonList(
                Pooling.builder()
                        .id(22L)
                        .name("Pooling 22")
                        .build()
        );

        List<Lead> leads = Arrays.asList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(poolingsFirst)
                        .build(),
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(poolingsSecond)
                        .build()
        );

        //act
        List actual = (List) entityTransformer.transform(leads);

        //assert
        List<Lead> expected = Collections.singletonList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(
                                Arrays.asList(
                                        Pooling.builder()
                                                .id(11L)
                                                .name("Pooling 11")
                                                .build(),
                                        Pooling.builder()
                                                .id(22L)
                                                .name("Pooling 22")
                                                .build())
                        )
                        .build());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void transform_DuplicateNestedElementsList1_ReturnsUniqueList() {
        //arrange

        PoolingTract poolingTract1 = PoolingTract.builder()
                .id(111L)
                .name("Pooling Tract 111")
                .build();

        PoolingTract poolingTract2 = PoolingTract.builder()
                .id(112L)
                .name("Pooling Tract 112")
                .build();

        List<Pooling> poolingsFirst = Collections.singletonList(
                Pooling.builder()
                        .id(11L)
                        .name("Pooling 11")
                        .poolingTracts(Collections.singletonList(poolingTract1))
                        .build()
        );

        List<Pooling> poolingsSecond = Collections.singletonList(
                Pooling.builder()
                        .id(11L)
                        .name("Pooling 11")
                        .poolingTracts(Collections.singletonList(poolingTract2))
                        .build()
        );

        List<Lead> leads = Arrays.asList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(poolingsFirst)
                        .build(),
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(poolingsSecond)
                        .build()
        );

        //act
        List actual = (List) entityTransformer.transform(leads);

        //assert
        List<Lead> expected = Collections.singletonList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(
                                Arrays.asList(
                                        Pooling.builder()
                                                .id(11L)
                                                .name("Pooling 11")
                                                .poolingTracts(
                                                        Arrays.asList(
                                                                PoolingTract.builder()
                                                                        .id(111L)
                                                                        .name("Pooling Tract 111")
                                                                        .build(),
                                                                PoolingTract.builder()
                                                                        .id(112L)
                                                                        .name("Pooling Tract 112")
                                                                        .build()))
                                                .build())
                        )
                        .build());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void transform_DuplicateNestedElementsList2_ReturnsUniqueList() {
        //arrange
        PoolingTract poolingTract1 = PoolingTract.builder()
                .id(111L)
                .name("Pooling Tract 111")
                .build();

        PoolingTract poolingTract2 = PoolingTract.builder()
                .id(112L)
                .name("Pooling Tract 112")
                .build();

        PoolingTract poolingTract3 = PoolingTract.builder()
                .id(113L)
                .name("Pooling Tract 113")
                .build();

        List<Pooling> poolingsFirst = Collections.singletonList(
                Pooling.builder()
                        .id(11L)
                        .name("Pooling 11")
                        .poolingTracts(Collections.singletonList(poolingTract1))
                        .build()
        );

        List<Pooling> poolingsSecond = Collections.singletonList(
                Pooling.builder()
                        .id(11L)
                        .name("Pooling 11")
                        .poolingTracts(Arrays.asList(poolingTract2, poolingTract3))
                        .build()
        );

        List<Lead> leads = Arrays.asList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(poolingsFirst)
                        .build(),
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(poolingsSecond)
                        .build()
        );

        //act
        List actual = (List) entityTransformer.transform(leads);

        //assert
        List<Lead> expected = Collections.singletonList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(
                                Arrays.asList(
                                        Pooling.builder()
                                                .id(11L)
                                                .name("Pooling 11")
                                                .poolingTracts(
                                                        Arrays.asList(
                                                                PoolingTract.builder()
                                                                        .id(111L)
                                                                        .name("Pooling Tract 111")
                                                                        .build(),
                                                                PoolingTract.builder()
                                                                        .id(112L)
                                                                        .name("Pooling Tract 112")
                                                                        .build(),
                                                                PoolingTract.builder()
                                                                        .id(113L)
                                                                        .name("Pooling Tract 113")
                                                                        .build()))
                                                .build())
                        )
                        .build());

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void transform_DuplicateNestedElementsList3_ReturnsUniqueList() {
        //arrange

        Quartering q1 = Quartering.builder().id(1111L).name("Q 1").build();
        Quartering q2 = Quartering.builder().id(1112L).name("Q 2").build();
        Quartering q3 = Quartering.builder().id(1113L).name("Q 3").build();
        Quartering q4 = Quartering.builder().id(1114L).name("Q 4").build();
        Quartering q5 = Quartering.builder().id(1115L).name("Q 5").build();
        Quartering q6 = Quartering.builder().id(1116L).name("Q 6").build();

        PoolingTract poolingTract1 = PoolingTract.builder()
                .id(111L)
                .name("Pooling Tract 111")
                .quarterings(Sets.newLinkedHashSet(q1, q2, q3))
                .build();

        PoolingTract poolingTract2 = PoolingTract.builder()
                .id(112L)
                .name("Pooling Tract 112")
                .quarterings(Sets.newLinkedHashSet(q1, q2, q3))
                .build();

        PoolingTract poolingTract3 = PoolingTract.builder()
                .id(113L)
                .name("Pooling Tract 113")
                .quarterings(Sets.newLinkedHashSet(q4, q5, q6))
                .build();

        List<Pooling> poolingsFirst = Collections.singletonList(
                Pooling.builder()
                        .id(11L)
                        .name("Pooling 11")
                        .poolingTracts(Collections.singletonList(poolingTract1))
                        .build()
        );

        List<Pooling> poolingsSecond = Collections.singletonList(
                Pooling.builder()
                        .id(12L)
                        .name("Pooling 12")
                        .poolingTracts(Arrays.asList(poolingTract2, poolingTract3))
                        .build()
        );

        List<Lead> leads = Arrays.asList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(poolingsFirst)
                        .build(),
                Lead.builder()
                        .id(2L)
                        .name("Lead 2")
                        .poolings(poolingsSecond)
                        .build()
        );

        //act
        List actual = (List) entityTransformer.transform(leads);

        //assert
        List<Lead> expected = Arrays.asList(
                Lead.builder()
                        .id(1L)
                        .name("Lead 1")
                        .poolings(
                                Arrays.asList(
                                        Pooling.builder()
                                                .id(11L)
                                                .name("Pooling 11")
                                                .poolingTracts(
                                                        Arrays.asList(
                                                                PoolingTract.builder()
                                                                        .id(111L)
                                                                        .name("Pooling Tract 111")
                                                                        .quarterings(
                                                                                Sets.newLinkedHashSet(
                                                                                        Quartering.builder().id(1111L).name("Q 1").build(),
                                                                                        Quartering.builder().id(1112L).name("Q 2").build(),
                                                                                        Quartering.builder().id(1113L).name("Q 3").build()
                                                                                )
                                                                        )
                                                                        .build()))
                                                .build())
                        )
                        .build(),
                Lead.builder()
                        .id(2L)
                        .name("Lead 2")
                        .poolings(
                                Arrays.asList(
                                        Pooling.builder()
                                                .id(12L)
                                                .name("Pooling 12")
                                                .poolingTracts(
                                                        Arrays.asList(
                                                                PoolingTract.builder()
                                                                        .id(112L)
                                                                        .name("Pooling Tract 112")
                                                                        .quarterings(
                                                                                Sets.newLinkedHashSet(
                                                                                        Quartering.builder().id(1111L).name("Q 1").build(),
                                                                                        Quartering.builder().id(1112L).name("Q 2").build(),
                                                                                        Quartering.builder().id(1113L).name("Q 3").build()
                                                                                )
                                                                        )
                                                                        .build(),
                                                                PoolingTract.builder()
                                                                        .id(113L)
                                                                        .name("Pooling Tract 113")
                                                                        .quarterings(
                                                                                Sets.newLinkedHashSet(
                                                                                        Quartering.builder().id(1114L).name("Q 4").build(),
                                                                                        Quartering.builder().id(1115L).name("Q 5").build(),
                                                                                        Quartering.builder().id(1116L).name("Q 6").build()
                                                                                )
                                                                        )
                                                                        .build()))
                                                .build())
                        )
                        .build());

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}