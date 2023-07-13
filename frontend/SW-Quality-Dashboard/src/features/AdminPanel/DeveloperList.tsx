import React from 'react';
import { Box, Text, Divider } from '@chakra-ui/react';
import { Developer } from '../../pages/AdminPanel';

export function DeveloperList({ developers }: { developers: Developer[]; }) {
    return (
        <Box bg="gray.100" p={4} mb={4}>
            <Text fontWeight="bold" fontSize="lg" mb={2}>
                Developers
            </Text>
            {developers.map((developer, index) => (
                <Box key={index}>
                    {index > 0 && <Divider my={4} />}
                    <Text fontWeight="bold" mb={2}>
                        Name: {developer.name}
                    </Text>
                    <Text fontWeight="bold" mb={2}>
                        GitHub URL: {developer.githubUrl}
                    </Text>
                    <Text fontWeight="bold" mb={2}>
                        Total Commits: {developer.totalCommits}
                    </Text>
                </Box>
            ))}
        </Box>
    );
}
