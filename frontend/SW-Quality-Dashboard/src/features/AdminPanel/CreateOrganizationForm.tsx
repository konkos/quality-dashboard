import React, { useState } from 'react';
import { Box, Text, Input, FormControl, FormLabel, Button } from '@chakra-ui/react';

export function CreateOrganizationForm() {
    //chakra-ui form to create the type organisation
    const [name, setName] = useState<string>('');

    const handleCreateOrganization = () => {
        // Create organization
    };
    return (
        <>
            <Box bg="gray.100" p={4} mb={4}>
                <Text fontWeight="bold" fontSize="lg" mb={2}>
                    Create Organization
                </Text>
                <FormControl>
                    <FormLabel>Your Organisation</FormLabel>
                    <Input placeholder="Enter Organization Name" value={name} onChange={(e) => setName(e.target.value)} />
                    <Button mt={4} type="submit" w={"15%"} onClick={() => handleCreateOrganization()} variant="solid" colorScheme='green'> Create</Button>
                </FormControl>


            </Box>
        </>
    );
}
