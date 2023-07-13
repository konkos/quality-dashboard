import React from 'react'
import { Box, Flex, Text, Button } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
function Navbar() {
    return (
        <Box p={4} borderBottom={"solid black 2px"}>
            <Flex justifyContent="space-between" alignItems="center">

                <Link to="/">
                    <Button variant="ghost">
                        <Text fontSize="xl" fontWeight="bold">SW-Dashboard </Text>
                    </Button>
                </Link>

                <Flex gap={4}>
                    <Link to="/admin-panel">
                        <Button variant="ghost">Admin Panel</Button>
                    </Link>
                    <Link to="/submit-project">
                        <Button variant="ghost">Submit Project</Button>
                    </Link>
                    <Link to="/register">
                        <Button variant="ghost">Register</Button>
                    </Link>
                    <Link to="/about">
                        <Button variant="ghost">About</Button>
                    </Link>


                </Flex>
            </Flex>
        </Box>
    )
}

export default Navbar