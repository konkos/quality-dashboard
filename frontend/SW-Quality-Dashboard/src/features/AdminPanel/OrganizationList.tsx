
import { Box, Text } from '@chakra-ui/react';
import { Organization } from '../../pages/AdminPanel';

export function OrganizationList({ organization }: { organization: Organization; }) {
    return (
        <Box bg="gray.100" p={4} mb={4}>
            <Text fontWeight="bold" fontSize="lg" mb={2}>
                Organization ID: {organization.id}
            </Text>
            <Text fontWeight="bold" mb={2}>
                Name: {organization.name}
            </Text>

            <Text fontWeight="bold" mb={2}>
                Users:
            </Text>
            <ul>
                {organization.users.map((user, index) => (
                    <li key={index}>{user.name}</li>
                ))}
            </ul>

            <Text fontWeight="bold" mb={2}>
                Repo URLs:
            </Text>
            <ul>
                {organization.projects.map((project) => (
                    <li key={project.id}>{project.repoUrl}</li>
                ))}
            </ul>
        </Box>
    );
}
