# Snowflake

A snowflake is a source of k-ordered unique 64-bit integers.

This is a Java library for generating ids in an un-coordinated setting:

    int node = 1;
    Snowflake s = new Snowflake(node);
    long id = s.next();

The node id is a manually assigned value between 0 and 1023 which is
 used to differentiate different snowflakes when used in a multi-node cluster.
