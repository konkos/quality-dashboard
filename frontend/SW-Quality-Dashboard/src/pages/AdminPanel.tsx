import React, { useEffect, useState } from 'react';
import { Box, Text, VStack } from '@chakra-ui/react';
import { PendingProjectsList } from '../features/AdminPanel/PendingProjectsList';
import { CreateOrganizationForm } from '../features/AdminPanel/CreateOrganizationForm';
import { OrganizationList } from '../features/AdminPanel/OrganizationList';
import { DeveloperList } from '../features/AdminPanel/DeveloperList';

export interface Organization {
    id: number;
    name: string;
    users: User[];
    projects: Project[];
    organizationAnalysis: OrganizationAnalysis;
    developers: Developer[];
}

interface User {
    name: string;
}

export interface Project {
    id: number;
    repoUrl: string;
}

export interface Developer {
    name: string;
    githubUrl: string;
    totalCommits: number;
}

interface OrganizationAnalysis {
    // Define properties for OrganizationAnalysis type
}

function AdminPanel() {
    const [organizations, setOrganizations] = useState<Organization[]>([]);

    useEffect(() => {
        // Dummy data for organizations
        const organizationData: Organization[] = [
            {
                id: 1,
                name: 'Organization 1',
                users: [{ name: 'User 1' }, { name: 'User 2' }],
                projects: [
                    { id: 1, repoUrl: 'https://github.com/project1' },
                    { id: 2, repoUrl: 'https://github.com/project2' },
                ],
                organizationAnalysis: {},
                developers: [
                    {
                        name: 'Developer 1',
                        githubUrl: 'https://github.com/developer1',
                        totalCommits: 10,
                    },
                    {
                        name: 'Developer 2',
                        githubUrl: 'https://github.com/developer2',
                        totalCommits: 5,
                    },
                ],
            },
            {
                id: 2,
                name: 'Organization 2',
                users: [{ name: 'User 3' }, { name: 'User 4' }],
                projects: [
                    { id: 3, repoUrl: 'https://github.com/project3' },
                    { id: 4, repoUrl: 'https://github.com/project4' },
                ],
                organizationAnalysis: {},
                developers: [
                    {
                        name: 'Developer 3',
                        githubUrl: 'https://github.com/developer3',
                        totalCommits: 15,
                    },
                    {
                        name: 'Developer 4',
                        githubUrl: 'https://github.com/developer4',
                        totalCommits: 20,
                    },
                ],
            },
        ];

        setOrganizations(organizationData);
    }, []);

    return (
        <Box p={4}>
            <CreateOrganizationForm />
            <Text fontSize="2xl" fontWeight="bold" mb={4}>
                Admin Panel
            </Text>

            <Text fontSize={"xl"} fontWeight={"bold"} mb={4}>
                Pending Projects
            </Text>
            <PendingProjectsList />

            <Text fontSize={"xl"} fontWeight={"bold"}>
                Organizations
            </Text>
            {organizations.map((organization) => (
                <OrganizationList key={organization.id} organization={organization} />
            ))}

            <VStack align="stretch">
                {organizations.map((organization) => (
                    <DeveloperList key={organization.id} developers={organization.developers} />
                ))}
            </VStack>
        </Box>
    );
}

export default AdminPanel;
