import { Button, Flex, chakra } from '@chakra-ui/react'
import { useState } from 'react'
import AnimatedCount from '../../components/AnimatedCount'
function ShowTotalProjects() {
    const [totalCommits, setTotalCommits] = useState(0)
    return (
        <Flex direction={"column"} p={4}>
            <chakra.span fontWeight={"bold"}>Total Projects</chakra.span>
            <AnimatedCount count={totalCommits} />
            <Button my={4} width="25%" onClick={() => setTotalCommits(totalCommits + 15)}>Increment</Button>
        </Flex>
    )
}


export default ShowTotalProjects