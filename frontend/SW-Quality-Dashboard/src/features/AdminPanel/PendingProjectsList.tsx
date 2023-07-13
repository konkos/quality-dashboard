import React, { useEffect, useState } from 'react';
import { Flex, Box } from '@chakra-ui/react';
import { Project } from '../../pages/AdminPanel';

export function PendingProjectsList() {
    //pending projects
    const [projects, setProjects] = useState<Project[]>([]);
    useEffect(() => {
        // Dummy data for projects
        const projectData: Project[] = [
            { id: 1, repoUrl: "" },
            { id: 2, repoUrl: "" },
        ];
        setProjects(projectData);
    }, []);
    return (
        <Flex direction={"column"}>
            {projects.map((project, index) => (
                <Box>
                    Here goes the project list
                </Box>
            ))}
        </Flex>
    );
}
